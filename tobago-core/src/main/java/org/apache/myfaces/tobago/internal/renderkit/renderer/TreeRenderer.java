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

import org.apache.myfaces.tobago.context.Markup;
import org.apache.myfaces.tobago.internal.component.AbstractUIData;
import org.apache.myfaces.tobago.internal.component.AbstractUIStyle;
import org.apache.myfaces.tobago.internal.component.AbstractUITree;
import org.apache.myfaces.tobago.internal.component.AbstractUITreeNode;
import org.apache.myfaces.tobago.internal.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.internal.util.JsonUtils;
import org.apache.myfaces.tobago.internal.util.RenderUtils;
import org.apache.myfaces.tobago.model.ExpandedState;
import org.apache.myfaces.tobago.model.Selectable;
import org.apache.myfaces.tobago.model.SelectedState;
import org.apache.myfaces.tobago.model.TreePath;
import org.apache.myfaces.tobago.renderkit.RendererBase;
import org.apache.myfaces.tobago.renderkit.css.TobagoClass;
import org.apache.myfaces.tobago.renderkit.html.DataAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlElements;
import org.apache.myfaces.tobago.renderkit.html.HtmlInputTypes;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class TreeRenderer extends RendererBase {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  protected static final String SCROLL_POSITION = "scrollPosition";

  @Override
  public void decode(final FacesContext facesContext, final UIComponent component) {
    final AbstractUITree tree = (AbstractUITree) component;
    final String value = facesContext.getExternalContext().getRequestParameterMap().get(
        tree.getClientId(facesContext) + ComponentUtils.SUB_SEPARATOR + SCROLL_POSITION);
    if (value != null) {
      tree.getState().getScrollPosition().update(value);
    }
    RenderUtils.decodedStateOfTreeData(facesContext, tree);
  }

  @Override
  public boolean getRendersChildren() {
    return true;
  }

  @Override
  public void encodeChildren(final FacesContext facesContext, final UIComponent component) throws IOException {
    final AbstractUITree tree = (AbstractUITree) component;
    for (final UIComponent child : tree.getChildren()) {
      if (child instanceof AbstractUIStyle) {
        child.encodeAll(facesContext);
      }
    }
  }

  @Override
  public void encodeEnd(final FacesContext facesContext, final UIComponent component) throws IOException {

    final TobagoResponseWriter writer = getResponseWriter(facesContext);
    final AbstractUITree tree = (AbstractUITree) component;
    final String clientId = tree.getClientId(facesContext);
    final Markup markup = tree.getMarkup();
    final UIComponent root = ComponentUtils.findDescendant(tree, AbstractUITreeNode.class);
    if (root == null) {
      LOG.error("Can't find the tree root. This may occur while updating a tree from Tobago 1.0 to 1.5. "
          + "Please refer the documentation to see how to use tree tags.");
      return;
    }

    writer.startElement(HtmlElements.DIV);
    writer.writeIdAttribute(clientId);
    writer.writeClassAttribute(
        TobagoClass.TREE,
        TobagoClass.TREE.createMarkup(markup),
        tree.getCustomClass());
    HtmlRendererUtils.writeDataAttributes(facesContext, writer, tree);
    writer.writeAttribute(DataAttributes.SCROLL_PANEL, Boolean.TRUE.toString(), false);

    final Selectable selectable = tree.getSelectable();
    if (selectable.isSupportedByTree()) {
      writer.writeAttribute(DataAttributes.SELECTABLE, selectable.name(), false);
    }

    final SelectedState selectedState = tree.getSelectedState();
    final List<Integer> selectedValue = new ArrayList<>();

    final ExpandedState expandedState = tree.getExpandedState();
    final List<Integer> expandedValue = new ArrayList<>();

    final int last = tree.isRowsUnlimited() ? Integer.MAX_VALUE : tree.getFirst() + tree.getRows();
    for (int rowIndex = tree.getFirst(); rowIndex < last; rowIndex++) {
      tree.setRowIndex(rowIndex);
      if (!tree.isRowAvailable()) {
        break;
      }

      final TreePath path = tree.getPath();

      if (selectedState.isSelected(path)) {
        selectedValue.add(rowIndex);
      }

      if (tree.isFolder() && expandedState.isExpanded(path)) {
        expandedValue.add(rowIndex);
      }

      for (final UIComponent child : tree.getChildren()) {
        if (child instanceof AbstractUIStyle) {
          // ignore, this is rendered in encodeChildren()
        } else {
          child.encodeAll(facesContext);
        }
      }
    }
    tree.setRowIndex(-1);

    writer.startElement(HtmlElements.INPUT);
    writer.writeAttribute(HtmlAttributes.TYPE, HtmlInputTypes.HIDDEN);
    final String selectedId = clientId + ComponentUtils.SUB_SEPARATOR + AbstractUITree.SUFFIX_SELECTED;
    writer.writeNameAttribute(selectedId);
    writer.writeIdAttribute(selectedId);
    writer.writeClassAttribute(TobagoClass.TREE__SELECTED);
    writer.writeAttribute(HtmlAttributes.VALUE, JsonUtils.encode(selectedValue), false);
    writer.endElement(HtmlElements.INPUT);

    writer.startElement(HtmlElements.INPUT);
    writer.writeAttribute(HtmlAttributes.TYPE, HtmlInputTypes.HIDDEN);
    final String expandedId = clientId + ComponentUtils.SUB_SEPARATOR + AbstractUIData.SUFFIX_EXPANDED;
    writer.writeNameAttribute(expandedId);
    writer.writeIdAttribute(expandedId);
    writer.writeClassAttribute(TobagoClass.TREE__EXPANDED);
    writer.writeAttribute(HtmlAttributes.VALUE, JsonUtils.encode(expandedValue), false);
    writer.endElement(HtmlElements.INPUT);

    writer.startElement(HtmlElements.INPUT);
    writer.writeIdAttribute(clientId + ComponentUtils.SUB_SEPARATOR + SCROLL_POSITION);
    writer.writeNameAttribute(clientId + ComponentUtils.SUB_SEPARATOR + SCROLL_POSITION);
    writer.writeAttribute(HtmlAttributes.TYPE, HtmlInputTypes.HIDDEN);
    writer.writeAttribute(HtmlAttributes.VALUE, tree.getState().getScrollPosition().encode(), false);
    writer.writeAttribute(DataAttributes.SCROLL_POSITION, Boolean.TRUE.toString(), false);
    writer.endElement(HtmlElements.INPUT);

    writer.endElement(HtmlElements.DIV);
  }
}
