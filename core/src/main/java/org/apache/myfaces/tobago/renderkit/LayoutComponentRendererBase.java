package org.apache.myfaces.tobago.renderkit;

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

import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.config.ThemeConfig;
import org.apache.myfaces.tobago.layout.Measure;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public abstract class LayoutComponentRendererBase extends RendererBase implements LayoutComponentRenderer {

  public Measure getWidth(FacesContext facesContext, UIComponent component) {
    return ThemeConfig.getMeasure(facesContext, component, Attributes.WIDTH);
  }

  public Measure getHeight(FacesContext facesContext, UIComponent component) {
    return ThemeConfig.getMeasure(facesContext, component, Attributes.HEIGHT);
  }

  public Measure getMinimumWidth(FacesContext facesContext, UIComponent component) {
    return ThemeConfig.getMeasure(facesContext, component, Attributes.MINIMUM_WIDTH);
  }

  public Measure getMinimumHeight(FacesContext facesContext, UIComponent component) {
    return ThemeConfig.getMeasure(facesContext, component, Attributes.MINIMUM_HEIGHT);
  }

  public Measure getPreferredWidth(FacesContext facesContext, UIComponent component) {
    return ThemeConfig.getMeasure(facesContext, component, Attributes.PREFERRED_WIDTH);
  }

  public Measure getPreferredHeight(FacesContext facesContext, UIComponent component) {
    return ThemeConfig.getMeasure(facesContext, component, Attributes.PREFERRED_HEIGHT);
  }

  public Measure getMaximumWidth(FacesContext facesContext, UIComponent component) {
    return ThemeConfig.getMeasure(facesContext, component, Attributes.MAXIMUM_WIDTH);
  }

  public Measure getMaximumHeight(FacesContext facesContext, UIComponent component) {
    return ThemeConfig.getMeasure(facesContext, component, Attributes.MAXIMUM_HEIGHT);
  }
}
