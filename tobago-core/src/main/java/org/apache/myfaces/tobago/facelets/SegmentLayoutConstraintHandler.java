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

package org.apache.myfaces.tobago.facelets;

import org.apache.myfaces.tobago.component.Attributes;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;
import java.io.IOException;
import java.util.Map;

public class SegmentLayoutConstraintHandler extends TagHandler {

  private final TagAttribute offsetExtraSmall;
  private final TagAttribute offsetSmall;
  private final TagAttribute offsetMedium;
  private final TagAttribute offsetLarge;
  private final TagAttribute extraSmall;
  private final TagAttribute small;
  private final TagAttribute medium;
  private final TagAttribute large;

  public SegmentLayoutConstraintHandler(TagConfig config) {
    super(config);
    offsetExtraSmall = getAttribute(Attributes.offsetExtraSmall.getName());
    offsetSmall = getAttribute(Attributes.offsetSmall.getName());
    offsetMedium = getAttribute(Attributes.offsetMedium.getName());
    offsetLarge = getAttribute(Attributes.offsetLarge.getName());
    extraSmall = getAttribute(Attributes.extraSmall.getName());
    small = getAttribute(Attributes.small.getName());
    medium = getAttribute(Attributes.medium.getName());
    large = getAttribute(Attributes.large.getName());
  }

  @Override
  public void apply(FaceletContext faceletContext, UIComponent parent) throws IOException {
    final Map<String, Object> attributes = parent.getAttributes();

    if (offsetExtraSmall != null) {
      if (offsetExtraSmall.isLiteral()) {
        attributes.put(Attributes.offsetExtraSmall.getName(), offsetExtraSmall.getValue());
      } else {
        parent.setValueExpression(Attributes.offsetExtraSmall.getName(),
            offsetExtraSmall.getValueExpression(faceletContext, Integer.TYPE));
      }
    }

    if (offsetSmall != null) {
      if (offsetSmall.isLiteral()) {
        attributes.put(Attributes.offsetSmall.getName(), offsetSmall.getValue());
      } else {
        parent.setValueExpression(Attributes.offsetSmall.getName(),
            offsetSmall.getValueExpression(faceletContext, Integer.TYPE));
      }
    }

    if (offsetMedium != null) {
      if (offsetMedium.isLiteral()) {
        attributes.put(Attributes.offsetMedium.getName(), offsetMedium.getValue());
      } else {
        parent.setValueExpression(Attributes.offsetMedium.getName(),
            offsetMedium.getValueExpression(faceletContext, Integer.TYPE));
      }
    }

    if (offsetLarge != null) {
      if (offsetLarge.isLiteral()) {
        attributes.put(Attributes.offsetLarge.getName(), offsetLarge.getValue());
      } else {
        parent.setValueExpression(Attributes.offsetLarge.getName(),
            offsetLarge.getValueExpression(faceletContext, Integer.TYPE));
      }
    }

    if (extraSmall != null) {
      if (extraSmall.isLiteral()) {
        attributes.put(Attributes.extraSmall.getName(), extraSmall.getValue());
      } else {
        parent.setValueExpression(Attributes.extraSmall.getName(),
            extraSmall.getValueExpression(faceletContext, Integer.TYPE));
      }
    }

    if (small != null) {
      if (small.isLiteral()) {
        attributes.put(Attributes.small.getName(), small.getValue());
      } else {
        parent.setValueExpression(Attributes.small.getName(),
            small.getValueExpression(faceletContext, Integer.TYPE));
      }
    }

    if (medium != null) {
      if (medium.isLiteral()) {
        attributes.put(Attributes.medium.getName(), medium.getValue());
      } else {
        parent.setValueExpression(Attributes.medium.getName(),
            medium.getValueExpression(faceletContext, Integer.TYPE));
      }
    }

    if (large != null) {
      if (large.isLiteral()) {
        attributes.put(Attributes.large.getName(), large.getValue());
      } else {
        parent.setValueExpression(Attributes.large.getName(),
            large.getValueExpression(faceletContext, Integer.TYPE));
      }
    }
  }
}
