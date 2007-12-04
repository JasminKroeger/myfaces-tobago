package org.apache.myfaces.tobago.event;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_HEIGHT;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_LEFT;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_TOP;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_WIDTH;
import static org.apache.myfaces.tobago.TobagoConstants.FACET_PICKER_POPUP;
import org.apache.myfaces.tobago.component.ComponentUtil;
import org.apache.myfaces.tobago.config.ThemeConfig;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
import javax.faces.event.ActionEvent;
import java.util.Map;
import java.util.StringTokenizer;

/*
 * User: weber
 * Date: Jul 18, 2005
 * Time: 3:24:46 PM
 */
public class DatePickerController extends MethodBinding {

  private static final Log LOG = LogFactory.getLog(DatePickerController.class);
  public static final String OPEN_POPUP = "openPopup";
  public static final String CLOSE_POPUP = "closePopup";

  public DatePickerController() {
  }

  public Object invoke(FacesContext facesContext, Object[] objects)
      throws EvaluationException {

    if (objects[0] instanceof ActionEvent) {

      UICommand command = (UICommand) ((ActionEvent) objects[0]).getSource();
      final String commandId = command.getClientId(facesContext);

      if (commandId.endsWith(OPEN_POPUP)) {

        final UIComponent popup
            = command.getFacet(FACET_PICKER_POPUP);
        if (popup != null) {
          popup.setRendered(true);
          final String dimensionParameter
              = commandId.substring(0, commandId.length() - OPEN_POPUP.length())
              + "Dimension";
          final String dimension = (String) facesContext.getExternalContext()
              .getRequestParameterMap().get(dimensionParameter);

          StringTokenizer st = new StringTokenizer(dimension, "x:");
          int width = nextDimensionToken(st,
              ThemeConfig.getValue(facesContext, popup, "DefaultPageWidth"));
          int height = nextDimensionToken(st,
              ThemeConfig.getValue(facesContext, popup, "DefaultPageHeight"));
          int left = nextDimensionToken(st, -1);
          int top = nextDimensionToken(st, -1);
          int popupWidth = ComponentUtil.getIntAttribute(
              popup, ATTR_WIDTH, -1);
          int popupHeight = ComponentUtil.getIntAttribute(
              popup, ATTR_HEIGHT, -1);
          int popupLeft = ComponentUtil.getIntAttribute(
              popup, ATTR_LEFT, -1);
          int popupTop = ComponentUtil.getIntAttribute(
              popup, ATTR_TOP, -1);

          final Map<String, String> attributes = popup.getAttributes();
          if (popupWidth == -1) {
            popupWidth = ThemeConfig.getValue(
                facesContext, popup, "CalendarPopupWidth");
            attributes.put(
                ATTR_WIDTH, String.valueOf(popupWidth));
          }
          if (popupHeight == -1) {
            popupHeight = ThemeConfig.getValue(
                facesContext, popup, "CalendarPopupHeight");
            attributes.put(
                ATTR_HEIGHT, String.valueOf(popupHeight));
          }

          if (popupLeft == -1) {
            if (left != -1) {
              int popupRight = left + (popupWidth / 2);
              popupLeft = popupRight < width
                  ? popupRight - popupWidth
                  : width - popupWidth;
            } else {
              popupLeft = (width - popupWidth) / 2;
            }
            attributes.put(ATTR_LEFT,
                (popupLeft > 0 ? String.valueOf(popupLeft) : "0"));
          }

          if (popupTop == -1) {
            if (top != -1) {
              final int fixedHeight =
                  ThemeConfig.getValue(facesContext, command, "fixedHeight");
              int popupBottom = top + popupHeight + fixedHeight;
              popupTop = popupBottom < height
                  ? top + fixedHeight
                  : height - popupHeight;
            } else {
              popupTop = (height - popupHeight) / 2;
            }
            attributes.put(ATTR_TOP,
                (popupTop > 0 ? String.valueOf(popupTop) : "0"));
          }
        }
      }

    }
    return null;
  }

  private int nextDimensionToken(StringTokenizer st, int defaultValue) {
    if (st.hasMoreTokens()) {
      try {
        defaultValue = Integer.parseInt(st.nextToken());
      } catch (NumberFormatException e) {
        LOG.error("Caught: " + e.getMessage(), e);
      }
    }
    return defaultValue;
  }

  public Class getType(FacesContext facesContext)
      throws MethodNotFoundException {
    return String.class;
  }
}
