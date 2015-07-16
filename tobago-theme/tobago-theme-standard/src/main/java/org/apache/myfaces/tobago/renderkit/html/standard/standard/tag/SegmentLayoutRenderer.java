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

import org.apache.myfaces.tobago.component.Facets;
import org.apache.myfaces.tobago.component.UISegmentLayout;
import org.apache.myfaces.tobago.component.UIExtensionPanel;
import org.apache.myfaces.tobago.component.UILabel;
import org.apache.myfaces.tobago.internal.component.AbstractUISegmentLayout;
import org.apache.myfaces.tobago.layout.LayoutContainer;
import org.apache.myfaces.tobago.renderkit.RendererBase;
import org.apache.myfaces.tobago.renderkit.css.Css;
import org.apache.myfaces.tobago.renderkit.html.BootstrapClass;
import org.apache.myfaces.tobago.renderkit.html.HtmlElements;
import org.apache.myfaces.tobago.renderkit.html.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.renderkit.util.BootstrapCssGenerator;
import org.apache.myfaces.tobago.renderkit.util.RenderUtils;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.List;

/**
 * Renders the 12 columns layout.
 */
public class SegmentLayoutRenderer extends RendererBase {

  private static final Logger LOG = LoggerFactory.getLogger(SegmentLayoutRenderer.class);

  @Override
  public boolean getRendersChildren() {
    return true;
  }

  @Override
  public void encodeBegin(final FacesContext facesContext, final UIComponent component) throws IOException {
    final AbstractUISegmentLayout segmentLayout = (AbstractUISegmentLayout) component;
    final TobagoResponseWriter writer = HtmlRendererUtils.getTobagoResponseWriter(facesContext);

    writer.startElement(HtmlElements.DIV, segmentLayout);
//    writer.writeClassAttribute("row");
//    writer.writeClassAttribute(BootstrapClass.FORM_HORIZONTAL, BootstrapClass.CONTAINER_FLUID);
    writer.writeClassAttribute(BootstrapClass.FORM_GROUP);
//    writer.writeClassAttribute(BootstrapClass.ROW);
  }

  @Override
  public void encodeChildren(final FacesContext facesContext, final UIComponent component) throws IOException {
    final TobagoResponseWriter writer = HtmlRendererUtils.getTobagoResponseWriter(facesContext);
    final UISegmentLayout segmentLayout = (UISegmentLayout) component;

    UIComponent container = segmentLayout.getParent();
    if (container.getFacet(Facets.LAYOUT) == segmentLayout) {
      // case (old style): layout manager over facet
      LOG.warn("Using this layout via a facet is deprecated. Please put the layout around the components.");
    } else {
      // case (modern style): the segmentLayout contains the content to be layed out.
      container = segmentLayout;
    }

    if (container instanceof LayoutContainer && !((LayoutContainer) container).isLayoutChildren()) {
      return;
    }

    final List<UIComponent> children = container.getChildren();
    final BootstrapCssGenerator generator = new BootstrapCssGenerator(
        segmentLayout.getExtraSmall(),
        segmentLayout.getSmall(),
        segmentLayout.getMedium(),
        segmentLayout.getLarge());
    for (UIComponent child : children) {
      if (child.isRendered()) {
        if (child instanceof UIExtensionPanel) {
//          writer.startElement(HtmlElements.DIV, null);
//          writer.writeClassAttribute(BootstrapClass.FORM_GROUP); or
//          writer.writeClassAttribute(BootstrapClass.ROW);
          for (UIComponent subChild : child.getChildren()) {
            encodeChild(facesContext, writer, generator, subChild);
          }
//          writer.endElement(HtmlElements.DIV);
        } else {
          encodeChild(facesContext, writer, generator, child);
        }
      }
    }
  }

  private void encodeChild(
      final FacesContext facesContext, final TobagoResponseWriter writer,
      final BootstrapCssGenerator generator, final UIComponent child) throws IOException {
    if (child instanceof UILabel) {
      final UILabel label = (UILabel) child;
      Css currentCss = label.getCurrentCss();
      if (currentCss == null) {
        currentCss = new Css();
        label.setCurrentCss(currentCss);
      }
      generator.generate(currentCss);
      RenderUtils.encode(facesContext, child);
    } else {
      writer.startElement(HtmlElements.DIV, null);
      Css css = new Css();
      generator.generate(css);
      writer.writeClassAttribute(css.encode());
      RenderUtils.encode(facesContext, child);
      writer.endElement(HtmlElements.DIV);
    }
    generator.next();
  }

  @Override
  public void encodeEnd(final FacesContext facesContext, final UIComponent component) throws IOException {
    final TobagoResponseWriter writer = HtmlRendererUtils.getTobagoResponseWriter(facesContext);
    writer.endElement(HtmlElements.DIV);
  }

}