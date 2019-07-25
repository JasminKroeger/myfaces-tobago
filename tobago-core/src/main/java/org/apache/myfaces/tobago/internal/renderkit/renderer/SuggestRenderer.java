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

import org.apache.myfaces.tobago.internal.component.AbstractUIInput;
import org.apache.myfaces.tobago.internal.component.AbstractUISuggest;
import org.apache.myfaces.tobago.internal.util.JsonUtils;
import org.apache.myfaces.tobago.internal.util.SelectItemUtils;
import org.apache.myfaces.tobago.model.AutoSuggestItem;
import org.apache.myfaces.tobago.model.AutoSuggestItems;
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

import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SuggestRenderer extends RendererBase {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Override
  public void decode(final FacesContext facesContext, final UIComponent component) {
    final AbstractUISuggest suggest = (AbstractUISuggest) component;
    final String clientId = suggest.getClientId(facesContext);
    final Map<String, String> requestParameterMap = facesContext.getExternalContext().getRequestParameterMap();
    if (requestParameterMap.containsKey(clientId)) {
      final String query = requestParameterMap.get(clientId);
      if (LOG.isDebugEnabled()) {
        LOG.debug("suggest query='{}'", query);
      }
      // XXX this is for the old way: for "suggestMethod"
      final AbstractUIInput input = ComponentUtils.findAncestor(suggest, AbstractUIInput.class);
      if (input != null) {
        input.setSubmittedValue(query);
      }
      // this is the new way: for select items
      suggest.setQuery(query);
    }
  }

  @Override
  public void encodeBegin(final FacesContext facesContext, final UIComponent component) throws IOException {
    final AbstractUISuggest suggest = (AbstractUISuggest) component;
    final AbstractUIInput input = ComponentUtils.findAncestor(suggest, AbstractUIInput.class);
    final MethodExpression suggestMethodExpression = suggest.getSuggestMethodExpression();

    int totalCount = suggest.getTotalCount();
    final String[] array;

    if (suggestMethodExpression != null && input != null) { // old way (deprecated)
      final AutoSuggestItems autoSuggestItems
          = createAutoSuggestItems(suggestMethodExpression.invoke(facesContext.getELContext(), new Object[]{input}));
      final List<AutoSuggestItem> items = autoSuggestItems.getItems();

      if (totalCount == -1 || items.size() < totalCount) {
        totalCount = items.size();
      }

      array = new String[totalCount];
      for (int i = 0; i < totalCount; i++) {
        array[i] = items.get(i).getLabel();
      }
    } else {
      final List<SelectItem> items = SelectItemUtils.getItemList(facesContext, suggest);

      if (totalCount == -1 || items.size() < totalCount) {
        totalCount = items.size();
      }

      array = new String[totalCount];
      for (int i = 0; i < totalCount; i++) {
        array[i] = items.get(i).getLabel();
      }
    }

    final TobagoResponseWriter writer = getResponseWriter(facesContext);

    writer.startElement(HtmlElements.INPUT);
    writer.writeAttribute(HtmlAttributes.TYPE, HtmlInputTypes.HIDDEN);
    writer.writeClassAttribute(TobagoClass.SUGGEST);
    final String clientId = suggest.getClientId(facesContext);
    writer.writeIdAttribute(clientId);
    writer.writeNameAttribute(clientId);
    if (input != null) {
      writer.writeAttribute(DataAttributes.SUGGEST_FOR, input.getFieldId(facesContext), false);
    } else {
      LOG.error("No ancestor with type AbstractUIInput found for suggest id={}", clientId);
    }
    writer.writeAttribute(DataAttributes.SUGGEST_MIN_CHARS, suggest.getMinimumCharacters());
    writer.writeAttribute(DataAttributes.SUGGEST_DELAY, suggest.getDelay());
    writer.writeAttribute(DataAttributes.SUGGEST_MAX_ITEMS, suggest.getMaximumItems());
    writer.writeAttribute(DataAttributes.SUGGEST_UPDATE, suggest.isUpdate());
    writer.writeAttribute(DataAttributes.SUGGEST_TOTAL_COUNT, totalCount);
    writer.writeAttribute(DataAttributes.SUGGEST_DATA, JsonUtils.encode(array), true);

    if (LOG.isDebugEnabled()) {
      LOG.debug("suggest list: " + JsonUtils.encode(array));
    }

    writer.endElement(HtmlElements.INPUT);
  }

  private AutoSuggestItems createAutoSuggestItems(final Object object) {
    if (object instanceof AutoSuggestItems) {
      return (AutoSuggestItems) object;
    }
    final AutoSuggestItems autoSuggestItems = new AutoSuggestItems();
    if (object instanceof List && !((List) object).isEmpty()) {
      if (((List) object).get(0) instanceof AutoSuggestItem) {
        //noinspection unchecked
        autoSuggestItems.setItems((List<AutoSuggestItem>) object);
      } else if (((List) object).get(0) instanceof String) {
        final List<AutoSuggestItem> items = new ArrayList<>(((List) object).size());
        for (int i = 0; i < ((List) object).size(); i++) {
          final AutoSuggestItem item = new AutoSuggestItem();
          item.setLabel((String) ((List) object).get(i));
          item.setValue((String) ((List) object).get(i));
          items.add(item);
        }
        autoSuggestItems.setItems(items);
      } else {
        throw new ClassCastException("Can't create AutoSuggestItems from '" + object + "'. "
            + "Elements needs to be " + String.class.getName() + " or " + AutoSuggestItem.class.getName());
      }
    } else {
      autoSuggestItems.setItems(Collections.emptyList());
    }
    return autoSuggestItems;
  }

}
