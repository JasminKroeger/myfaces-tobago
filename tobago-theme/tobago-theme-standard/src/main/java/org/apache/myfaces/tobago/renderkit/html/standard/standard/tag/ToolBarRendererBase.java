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

package org.apache.myfaces.tobago.renderkit.html.standard.standard.tag;

import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.component.Facets;
import org.apache.myfaces.tobago.component.SelectBooleanCommand;
import org.apache.myfaces.tobago.component.SelectOneCommand;
import org.apache.myfaces.tobago.component.SupportsAccessKey;
import org.apache.myfaces.tobago.component.UIMenuSelectOne;
import org.apache.myfaces.tobago.component.UIToolBar;
import org.apache.myfaces.tobago.component.UIToolBarSeparator;
import org.apache.myfaces.tobago.context.Markup;
import org.apache.myfaces.tobago.context.ResourceManagerUtils;
import org.apache.myfaces.tobago.internal.component.AbstractUICommand;
import org.apache.myfaces.tobago.internal.component.AbstractUIMenu;
import org.apache.myfaces.tobago.internal.util.ObjectUtils;
import org.apache.myfaces.tobago.renderkit.LabelWithAccessKey;
import org.apache.myfaces.tobago.renderkit.RendererBase;
import org.apache.myfaces.tobago.renderkit.css.Classes;
import org.apache.myfaces.tobago.renderkit.css.TobagoClass;
import org.apache.myfaces.tobago.renderkit.html.Command;
import org.apache.myfaces.tobago.renderkit.html.CommandMap;
import org.apache.myfaces.tobago.renderkit.html.DataAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlElements;
import org.apache.myfaces.tobago.renderkit.html.HtmlInputTypes;
import org.apache.myfaces.tobago.renderkit.html.JsonUtils;
import org.apache.myfaces.tobago.renderkit.html.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.renderkit.util.RenderUtils;
import org.apache.myfaces.tobago.renderkit.util.SelectItemUtils;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.apache.myfaces.tobago.util.CreateComponentUtils;
import org.apache.myfaces.tobago.util.FacetUtils;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.util.List;

/** XXX
*/
@Deprecated
public abstract class ToolBarRendererBase extends RendererBase {

  private static final Logger LOG = LoggerFactory.getLogger(ToolBarRendererBase.class);

  @Override
  public void encodeEnd(final FacesContext context, final UIComponent component) throws IOException {
    final UIToolBar toolBar = (UIToolBar) component;

    final TobagoResponseWriter writer = getResponseWriter(context);

    for (final UIComponent command : toolBar.getChildren()) {
      if (command instanceof AbstractUICommand) {
        renderToolbarCommand(context, toolBar, (AbstractUICommand) command, writer);
      } else if (command instanceof UIToolBarSeparator) {
        renderSeparator(context, toolBar, (UIToolBarSeparator) command, writer);
      } else {
        LOG.error("Illegal UIComponent class in toolbar (not a AbstractUICommand):" + command.getClass().getName());
      }
    }
  }

  private void renderToolbarCommand(
      final FacesContext facesContext, final UIToolBar toolBar, final AbstractUICommand command,
      final TobagoResponseWriter writer) throws IOException {
    if (command instanceof SelectBooleanCommand) {
      renderSelectBoolean(facesContext, toolBar, command, writer);
    } else if (command instanceof SelectOneCommand) {
      renderSelectOne(facesContext, toolBar, command, writer);
    } else {
      if (ComponentUtils.getFacet(command, Facets.radio) != null) {
        renderSelectOne(facesContext, toolBar, command, writer);
      } else if (ComponentUtils.getFacet(command, Facets.checkbox) != null) {
        renderSelectBoolean(facesContext, toolBar, command, writer);
      } else {
        final CommandMap map = new CommandMap(new Command(facesContext, command));
        renderToolbarButton(
            facesContext, toolBar, command, writer, false, map, null);
      }
    }
  }

  // todo: remove component creation in renderer, for JSF 2.0
  // todo: One solution is to make <tx:toolBarSelectOne> instead of <tc:toolBarSelectOne>
  private void renderSelectOne(
      final FacesContext facesContext, final UIToolBar toolBar, final AbstractUICommand command,
      final TobagoResponseWriter writer) throws IOException {

    final List<SelectItem> items;

    UIMenuSelectOne radio = (UIMenuSelectOne) ComponentUtils.getFacet(command, Facets.radio);
    if (radio == null) {
      items = SelectItemUtils.getItemList(facesContext, command);
      radio = CreateComponentUtils.createUIMenuSelectOneFacet(facesContext, command, null);
      radio.setId(facesContext.getViewRoot().createUniqueId());
    } else {
      items = SelectItemUtils.getItemList(facesContext, radio);
    }

    writer.startElement(HtmlElements.SPAN);
    writer.writeClassAttribute(Classes.create(toolBar, "selectOne"));
    final Object value = radio.getValue();

    String currentValue = "";
    boolean markFirst = !hasSelectedValue(items, value);
    final String radioId = radio.getClientId(facesContext);
    for (final SelectItem item : items) {
      final String labelText = item.getLabel();
      if (labelText != null) {
        ComponentUtils.setAttribute(command, Attributes.label, labelText);
      } else {
        LOG.warn("Menu item has label=null. UICommand.getClientId()=" + command.getClientId(facesContext));
      }

      String image = null;
      if (item instanceof org.apache.myfaces.tobago.model.SelectItem) {
        image = ((org.apache.myfaces.tobago.model.SelectItem) item).getImage();
      }
      if (image == null) {
        image = "image/1x1";
      }
      ComponentUtils.setAttribute(command, Attributes.image, image);

      if (item.getDescription() != null) {
        ComponentUtils.setAttribute(command, Attributes.tip, item.getDescription());
      }

      final String formattedValue = ComponentUtils.getFormattedValue(facesContext, radio, item.getValue());
      final boolean checked;
      if (ObjectUtils.equals(item.getValue(), value) || markFirst) {
        checked = true;
        markFirst = false;
        currentValue = formattedValue;
      } else {
        checked = false;
      }

      final CommandMap map = new CommandMap(new Command());
      renderToolbarButton(
          facesContext, toolBar, command, writer, checked, map, formattedValue);
    }

    writer.startElement(HtmlElements.INPUT);
    writer.writeAttribute(HtmlAttributes.TYPE, HtmlInputTypes.HIDDEN);
    writer.writeNameAttribute(radioId);
    writer.writeAttribute(HtmlAttributes.VALUE, currentValue, true);
    writer.endElement(HtmlElements.INPUT);
    writer.endElement(HtmlElements.SPAN);
  }

  // todo: remove component creation in renderer, for JSF 2.0
  // todo: One solution is to make <tx:toolBarCheck> instead of <tc:toolBarCheck>
  // may be renamed to toolBarSelectBoolean?
  private void renderSelectBoolean(
      final FacesContext facesContext, final UIToolBar toolBar, final AbstractUICommand command,
      final TobagoResponseWriter writer) throws IOException {

    UIComponent checkbox = ComponentUtils.getFacet(command, Facets.checkbox);
    if (checkbox == null) {
      checkbox = CreateComponentUtils.createUISelectBooleanFacetWithId(facesContext, command);
    }

    final boolean checked = ComponentUtils.getBooleanAttribute(checkbox, Attributes.value);
    final String clientId = checkbox.getClientId(facesContext);

    writer.startElement(HtmlElements.SPAN);
    writer.writeClassAttribute(Classes.create(toolBar, "selectBoolean"));
    final CommandMap map = new CommandMap(new Command());
    renderToolbarButton(facesContext, toolBar, command, writer, checked, map, null);

    writer.startElement(HtmlElements.INPUT);
    writer.writeAttribute(HtmlAttributes.TYPE, HtmlInputTypes.HIDDEN);
    writer.writeNameAttribute(clientId);
    writer.writeAttribute(HtmlAttributes.VALUE, Boolean.toString(checked), false);
    writer.endElement(HtmlElements.INPUT);
    writer.endElement(HtmlElements.SPAN);
  }

  private void renderToolbarButton(
      final FacesContext facesContext, final UIToolBar toolBar, final AbstractUICommand command,
      final TobagoResponseWriter writer,
      final boolean selected, final CommandMap map, final String value)
      throws IOException {
    if (!command.isRendered()) {
      return;
    }

    final boolean disabled = ComponentUtils.getBooleanAttribute(command, Attributes.disabled);
    final LabelWithAccessKey label = command instanceof SupportsAccessKey
        ? new LabelWithAccessKey(command)
        : new LabelWithAccessKey(command.getLabel());
    final AbstractUIMenu dropDownMenu = FacetUtils.getDropDownMenu(command);
    final boolean showDropDownMenu = dropDownMenu != null && dropDownMenu.isRendered();
    // two separate buttons for the command and the sub menu
    final boolean separateButtons = hasAnyCommand(command) && showDropDownMenu;

    // start rendering
    writer.startElement(HtmlElements.SPAN);
    Markup itemMarkup = Markup.NULL;
    if (selected) {
      itemMarkup = itemMarkup.add(Markup.SELECTED);
    }
    if (disabled) {
      itemMarkup = itemMarkup.add(Markup.DISABLED);
    }
    writer.writeClassAttribute(Classes.create(toolBar, "item", itemMarkup));
    final String title = HtmlRendererUtils.getTitleFromTipAndMessages(facesContext, command);
    if (title != null) {
      writer.writeAttribute(HtmlAttributes.TITLE, title, true);
    }

    writer.startElement(HtmlElements.SPAN);
    if (separateButtons || !showDropDownMenu) {
      writer.writeClassAttribute(Classes.create(toolBar, "button", selected ? Markup.SELECTED : Markup.NULL));
    } else {
      writer.writeClassAttribute(Classes.create(toolBar, "menu"));
    }
    if (!toolBar.isTransient()) {
      writer.writeIdAttribute(command.getClientId(facesContext));
    }
    if (map != null) {
      writer.writeAttribute(DataAttributes.COMMANDS, JsonUtils.encode(map), true);
    }
    HtmlRendererUtils.writeDataAttributes(facesContext, writer, command);
    if (value != null) {
      writer.writeAttribute(DataAttributes.VALUE, value, true);
    }

    if (separateButtons) {
      writer.endElement(HtmlElements.SPAN);

      writer.startElement(HtmlElements.SPAN);
      writer.writeClassAttribute(Classes.create(toolBar, "menu"));
      // todo: span has not type: use data-tobago-type here (TOBAGO-1004)
      writer.writeAttribute(HtmlAttributes.TYPE, HtmlInputTypes.BUTTON);
    }

    // render sub menu popup button
    if (showDropDownMenu) {
      writer.startElement(HtmlElements.IMG);
      final boolean dropDownDisabled
          = ComponentUtils.getBooleanAttribute(dropDownMenu, Attributes.disabled) || disabled;
      final String menuImage
          = ResourceManagerUtils.getImageOrDisabledImage(facesContext, "image/toolbarButtonMenu", dropDownDisabled);
      writer.writeAttribute(HtmlAttributes.SRC, menuImage, false);
      writer.endElement(HtmlElements.IMG);
      renderDropDownMenu(facesContext, writer, dropDownMenu);
    }
    writer.endElement(HtmlElements.SPAN);
    writer.endElement(HtmlElements.SPAN);
  }

  private void renderSeparator(
      final FacesContext facesContext, final UIToolBar toolBar, final UIToolBarSeparator separator,
      final TobagoResponseWriter writer)
      throws IOException {
    if (!separator.isRendered()) {
      return;
    }

    writer.startElement(HtmlElements.SPAN);
    writer.writeClassAttribute(Classes.create(toolBar, "item", Markup.DISABLED));

    writer.startElement(HtmlElements.SPAN);
    writer.writeClassAttribute(Classes.create(toolBar, "separator"));
    writer.endElement(HtmlElements.SPAN);

    writer.endElement(HtmlElements.SPAN);
  }

  private boolean hasAnyCommand(final AbstractUICommand command) {
    return !hasNoCommand(command);
  }

  private boolean hasNoCommand(final AbstractUICommand command) {
    return command.getActionExpression() == null
        && command.getActionListeners().length == 0
        && command.getLink() == null;
  }

  public static void renderDropDownMenu(
      final FacesContext facesContext, final TobagoResponseWriter writer, final AbstractUIMenu dropDownMenu)
      throws IOException {
    writer.startElement(HtmlElements.OL);
    // XXX fix naming conventions for CSS classes
    writer.writeClassAttribute(TobagoClass.MENU_BAR, TobagoClass.MENU__DROP_DOWN_MENU);
    RenderUtils.encode(facesContext, dropDownMenu);
    writer.endElement(HtmlElements.OL);
  }

  @Override
  public void encodeChildren(final FacesContext facesContext, final UIComponent component)
      throws IOException {
  }

  @Override
  public boolean getRendersChildren() {
    return true;
  }

  private boolean hasSelectedValue(final Iterable<SelectItem> items, final Object value) {
    for (final SelectItem item : items) {
      if (ObjectUtils.equals(item.getValue(), value)) {
        return true;
      }
    }
    return false;
  }

}
