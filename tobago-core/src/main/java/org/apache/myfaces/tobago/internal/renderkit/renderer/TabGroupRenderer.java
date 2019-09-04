/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.myfaces.tobago.internal.renderkit.renderer;

import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.component.ClientBehaviors;
import org.apache.myfaces.tobago.component.Facets;
import org.apache.myfaces.tobago.component.RendererTypes;
import org.apache.myfaces.tobago.component.Tags;
import org.apache.myfaces.tobago.context.Markup;
import org.apache.myfaces.tobago.event.TabChangeEvent;
import org.apache.myfaces.tobago.internal.behavior.EventBehavior;
import org.apache.myfaces.tobago.internal.component.AbstractUIEvent;
import org.apache.myfaces.tobago.internal.component.AbstractUIPanelBase;
import org.apache.myfaces.tobago.internal.component.AbstractUITab;
import org.apache.myfaces.tobago.internal.component.AbstractUITabGroup;
import org.apache.myfaces.tobago.internal.renderkit.CommandMap;
import org.apache.myfaces.tobago.internal.util.AccessKeyLogger;
import org.apache.myfaces.tobago.internal.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.internal.util.JsonUtils;
import org.apache.myfaces.tobago.internal.util.RenderUtils;
import org.apache.myfaces.tobago.model.SwitchType;
import org.apache.myfaces.tobago.renderkit.LabelWithAccessKey;
import org.apache.myfaces.tobago.renderkit.RendererBase;
import org.apache.myfaces.tobago.renderkit.css.BootstrapClass;
import org.apache.myfaces.tobago.renderkit.css.TobagoClass;
import org.apache.myfaces.tobago.renderkit.html.DataAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlElements;
import org.apache.myfaces.tobago.renderkit.html.HtmlInputTypes;
import org.apache.myfaces.tobago.renderkit.html.HtmlRoleValues;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@ListenerFor(systemEventClass = PostAddToViewEvent.class)
public class TabGroupRenderer extends RendererBase implements ComponentSystemEventListener {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private static final String ACTIVE_INDEX_POSTFIX = ComponentUtils.SUB_SEPARATOR + "activeIndex";

  @Override
  public void processEvent(final ComponentSystemEvent event) {

    final AbstractUITabGroup tabGroup = (AbstractUITabGroup) event.getComponent();

    for (final UIComponent child : tabGroup.getChildren()) {
      if (child instanceof AbstractUITab) {
        final AbstractUITab tab = (AbstractUITab) child;
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ClientBehaviors click = ClientBehaviors.click;
        switch (tabGroup.getSwitchType()) {
          case none:
            break;
          case client:
            // todo: implement a client behavior which can call local scripts (respect CSP)
            break;
          case reloadTab:
            final AjaxBehavior ajaxBehavior = new AjaxBehavior();
            final Collection<String> ids = Collections.singleton(
                UINamingContainer.getSeparatorChar(facesContext) + tabGroup.getClientId(facesContext));
            ajaxBehavior.setExecute(ids);
            ajaxBehavior.setRender(ids);
            tab.addClientBehavior(click.name(), ajaxBehavior);
            break;
          case reloadPage:
            final AbstractUIEvent component = (AbstractUIEvent) ComponentUtils.createComponent(
                facesContext, Tags.event.componentType(), RendererTypes.Event, "_click");
            component.setEvent(click);
            tab.getChildren().add(component);
            final EventBehavior eventBehavior = new EventBehavior();
            eventBehavior.setFor(component.getId());
            tab.addClientBehavior(click.name(), eventBehavior);
            break;
          default:
            LOG.error("Unknown switch type: '{}'", tabGroup.getSwitchType());
        }
      }
    }
  }

  @Override
  public void decode(final FacesContext facesContext, final UIComponent component) {
    if (ComponentUtils.isOutputOnly(component)) {
      return;
    }

    final int oldIndex = ((AbstractUITabGroup) component).getRenderedIndex();

    final String clientId = component.getClientId(facesContext);
    final Map parameters = facesContext.getExternalContext().getRequestParameterMap();
    final String newValue = (String) parameters.get(clientId + ACTIVE_INDEX_POSTFIX);
    try {
      final int activeIndex = Integer.parseInt(newValue);
      if (activeIndex != oldIndex) {
        final TabChangeEvent event = new TabChangeEvent(component, oldIndex, activeIndex);
        component.queueEvent(event);
      }
    } catch (final NumberFormatException e) {
      LOG.error("Can't parse activeIndex: '" + newValue + "'");
    }
  }

  @Override
  public void encodeEnd(final FacesContext facesContext, final UIComponent uiComponent) throws IOException {

    final AbstractUITabGroup tabGroup = (AbstractUITabGroup) uiComponent;

    final int activeIndex = ensureRenderedActiveIndex(facesContext, tabGroup);

    final String clientId = tabGroup.getClientId(facesContext);
    final String hiddenId = clientId + TabGroupRenderer.ACTIVE_INDEX_POSTFIX;
    final SwitchType switchType = tabGroup.getSwitchType();
    final Markup markup = tabGroup.getMarkup();
    final TobagoResponseWriter writer = getResponseWriter(facesContext);

    writer.startElement(HtmlElements.TOBAGO_TAB_GROUP);
    writer.writeIdAttribute(clientId);
    writer.writeClassAttribute(
        BootstrapClass.CARD,
        TobagoClass.TAB_GROUP.createMarkup(markup),
        tabGroup.getCustomClass(),
        markup != null && markup.contains(Markup.SPREAD) ? TobagoClass.SPREAD : null);
    HtmlRendererUtils.writeDataAttributes(facesContext, writer, tabGroup);
    writer.writeAttribute(DataAttributes.SWITCH_TYPE, switchType.name(), false);

    writer.startElement(HtmlElements.INPUT);
    writer.writeAttribute(HtmlAttributes.TYPE, HtmlInputTypes.HIDDEN);
    writer.writeAttribute(HtmlAttributes.VALUE, activeIndex);
    writer.writeNameAttribute(hiddenId);
    writer.writeIdAttribute(hiddenId);
    writer.endElement(HtmlElements.INPUT);

    if (tabGroup.isShowNavigationBar()) {
      encodeHeader(facesContext, writer, tabGroup, activeIndex, switchType);
    }

    encodeContent(facesContext, writer, tabGroup, activeIndex, switchType);

    writer.endElement(HtmlElements.TOBAGO_TAB_GROUP);
  }

  private int ensureRenderedActiveIndex(final FacesContext context, final AbstractUITabGroup tabGroup) {
    final int activeIndex = tabGroup.getSelectedIndex();
    // ensure to select a rendered tab
    int index = -1;
    int closestRenderedTabIndex = -1;
    for (final UIComponent tab : tabGroup.getChildren()) {
      if (tab instanceof AbstractUIPanelBase) {
        index++;
        if (index == activeIndex) {
          if (tab.isRendered()) {
            return index;
          } else if (closestRenderedTabIndex > -1) {
            break;
          }
        }
        if (tab.isRendered()) {
          closestRenderedTabIndex = index;
          if (index > activeIndex) {
            break;
          }
        }
      }
    }
    if (closestRenderedTabIndex == -1) {
      // resetting index to 0
      closestRenderedTabIndex = 0;
    }
    final ValueExpression expression = tabGroup.getValueExpression(Attributes.selectedIndex.getName());
    if (expression != null) {
      expression.setValue(context.getELContext(), closestRenderedTabIndex);
    } else {
      tabGroup.setSelectedIndex(closestRenderedTabIndex);
    }
    return closestRenderedTabIndex;
  }

  private void encodeHeader(
      final FacesContext facesContext, final TobagoResponseWriter writer, final AbstractUITabGroup tabGroup,
      final int activeIndex, final SwitchType switchType)
      throws IOException {

    final String tabGroupClientId = tabGroup.getClientId(facesContext);

    writer.startElement(HtmlElements.DIV);
    writer.writeClassAttribute(BootstrapClass.CARD_HEADER);
    writer.startElement(HtmlElements.UL);
    writer.writeClassAttribute(
        TobagoClass.TAB_GROUP__HEADER,
        BootstrapClass.NAV,
        BootstrapClass.NAV_TABS,
        BootstrapClass.CARD_HEADER_TABS);
    writer.writeAttribute(HtmlAttributes.ROLE, HtmlRoleValues.TABLIST.toString(), false);
    final CommandMap tabGroupMap = RenderUtils.getBehaviorCommands(facesContext, tabGroup);

    int index = 0;
    for (final UIComponent child : tabGroup.getChildren()) {
      if (child instanceof AbstractUITab) {
        final AbstractUITab tab = (AbstractUITab) child;
        if (tab.isRendered()) {
          final LabelWithAccessKey label = new LabelWithAccessKey(tab);
          final UIComponent labelFacet = ComponentUtils.getFacet(tab, Facets.label);
          final UIComponent barFacet = ComponentUtils.getFacet(tab, Facets.bar);
          final boolean disabled = tab.isDisabled();
          final String tabId = tab.getClientId(facesContext);
          Markup markup = tab.getMarkup() != null ? tab.getMarkup() : Markup.NULL;

          if (activeIndex == index) {
            markup = markup.add(Markup.SELECTED);
          }
          final FacesMessage.Severity maxSeverity
              = ComponentUtils.getMaximumSeverityOfChildrenMessages(facesContext, tab);
          if (maxSeverity != null) {
            markup = markup.add(ComponentUtils.markupOfSeverity(maxSeverity));
          }

          writer.startElement(HtmlElements.TOBAGO_TAB);
          writer.writeIdAttribute(tabId);
          writer.writeClassAttribute(
              TobagoClass.TAB,
              TobagoClass.TAB.createMarkup(markup),
              BootstrapClass.NAV_ITEM,
              barFacet != null ? TobagoClass.TAB__BAR_FACET : null,
              tab.getCustomClass());
          writer.writeAttribute(HtmlAttributes.FOR, tabGroupClientId, true);
          writer.writeAttribute(HtmlAttributes.ROLE, HtmlRoleValues.PRESENTATION.toString(), false);
          writer.writeAttribute(DataAttributes.TAB_GROUP_INDEX, index);
          final String title = HtmlRendererUtils.getTitleFromTipAndMessages(facesContext, tab);
          if (title != null) {
            writer.writeAttribute(HtmlAttributes.TITLE, title, true);
          }

          writer.startElement(HtmlElements.A);
          if (!tab.isDisabled()) {
            writer.writeAttribute(DataAttributes.TOGGLE, "tab", false);
          }
          if (tab.isDisabled()) {
            writer.writeClassAttribute(BootstrapClass.NAV_LINK, BootstrapClass.DISABLED);
          } else if (activeIndex == index) {
            writer.writeClassAttribute(BootstrapClass.NAV_LINK, BootstrapClass.ACTIVE);
          } else {
            writer.writeClassAttribute(BootstrapClass.NAV_LINK);
          }
          if (!disabled && switchType == SwitchType.client) {
            writer.writeAttribute(HtmlAttributes.HREF, '#' + getTabPanelId(facesContext, tab), false);
            writer.writeAttribute(
                DataAttributes.TARGET, '#' + getTabPanelId(facesContext, tab).replaceAll(":", "\\\\:"), false);
          }

          if (!disabled) {
            final CommandMap map = RenderUtils.getBehaviorCommands(facesContext, tab);
            CommandMap.merge(map, tabGroupMap);
            writer.writeAttribute(DataAttributes.COMMANDS, JsonUtils.encode(map), false);
          }

          if (!disabled && label.getAccessKey() != null) {
            writer.writeAttribute(HtmlAttributes.ACCESSKEY, Character.toString(label.getAccessKey()), false);
            AccessKeyLogger.addAccessKey(facesContext, label.getAccessKey(), tabId);
          }
          writer.writeAttribute(HtmlAttributes.ROLE, HtmlRoleValues.TAB.toString(), false);

          boolean labelEmpty = true;
          final String image = tab.getImage();
          // tab.getImage() resolves to empty string if el-expression resolves to null
          if (image != null && !image.isEmpty()) {
            writer.startElement(HtmlElements.IMG);
            writer.writeAttribute(HtmlAttributes.SRC, image, true);
// TBD      writer.writeClassAttribute(Classes.create(tab, (label.getLabel() != null? "image-right-margin" : "image")));
            writer.endElement(HtmlElements.IMG);
            labelEmpty = false;
          }
          if (label.getLabel() != null) {
            HtmlRendererUtils.writeLabelWithAccessKey(writer, label);
            labelEmpty = false;
          }
          if (labelFacet != null) {
            labelFacet.encodeAll(facesContext);
            labelEmpty = false;
          }
          if (labelEmpty) {
            writer.writeText(Integer.toString(index + 1));
          }
          writer.endElement(HtmlElements.A);

          if (barFacet != null) {
            writer.startElement(HtmlElements.DIV);
            barFacet.encodeAll(facesContext);
            writer.endElement(HtmlElements.DIV);
          }

          writer.endElement(HtmlElements.TOBAGO_TAB);
        }
        index++;
      }
    }
    writer.endElement(HtmlElements.UL);
    writer.endElement(HtmlElements.DIV);
  }

  protected void encodeContent(
      final FacesContext facesContext, final TobagoResponseWriter writer, final AbstractUITabGroup tabGroup,
      final int activeIndex, final SwitchType switchType) throws IOException {
    writer.startElement(HtmlElements.DIV);
    writer.writeClassAttribute(BootstrapClass.CARD_BODY, BootstrapClass.TAB_CONTENT);
    int index = 0;
    for (final UIComponent child : tabGroup.getChildren()) {
      if (child instanceof AbstractUITab) {
        final AbstractUITab tab = (AbstractUITab) child;
        if (tab.isRendered() && (switchType == SwitchType.client || index == activeIndex) && !tab.isDisabled()) {
          final Markup markup = tab.getMarkup();

          writer.startElement(HtmlElements.DIV);
          writer.writeClassAttribute(
              TobagoClass.TAB__CONTENT,
              TobagoClass.TAB__CONTENT.createMarkup(markup),
              BootstrapClass.TAB_PANE,
              index == activeIndex ? BootstrapClass.ACTIVE : null);
          writer.writeAttribute(HtmlAttributes.ROLE, HtmlRoleValues.TABPANEL.toString(), false);
          writer.writeIdAttribute(getTabPanelId(facesContext, tab));

          writer.writeAttribute(DataAttributes.TAB_GROUP_INDEX, index);

          tab.encodeAll(facesContext);

          writer.endElement(HtmlElements.DIV);
        }
        index++;
      }
    }
    writer.endElement(HtmlElements.DIV);
  }

  private String getTabPanelId(final FacesContext facesContext, final AbstractUITab tab) {
    return tab.getClientId(facesContext) + ComponentUtils.SUB_SEPARATOR + "content";
  }
}
