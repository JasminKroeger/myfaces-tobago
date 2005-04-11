/*
 * Copyright (c) 2003 Atanion GmbH, Germany
 * All rights reserved. Created 07.02.2003 16:00:00.
 * $Id$
 */
package com.atanion.tobago.renderkit.html.scarborough.standard.tag;

import com.atanion.lib.richtext.WikiParser;
import com.atanion.tobago.TobagoConstants;
import com.atanion.tobago.taglib.component.ToolBarTag;
import com.atanion.tobago.taglib.component.ToolBarSelectOneTag;
import com.atanion.tobago.taglib.component.CommandTag;
import com.atanion.tobago.taglib.component.ToolBarSelectBooleanTag;
import com.atanion.tobago.component.ComponentUtil;
import com.atanion.tobago.component.UICommand;
import com.atanion.tobago.context.ResourceManagerUtil;
import com.atanion.tobago.renderkit.HtmlUtils;
import com.atanion.tobago.renderkit.InputRendererBase;
import com.atanion.tobago.renderkit.html.HtmlRendererUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.application.Application;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.component.UIInput;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

public class RichTextEditorRenderer extends InputRendererBase {

// ///////////////////////////////////////////// constant

  private static final Log LOG = LogFactory.getLog(RichTextEditorRenderer.class);

  public static final String CHANGE_BUTTON = "togleState";

  public void decode(FacesContext facesContext, UIComponent component) {
    if (ComponentUtil.isOutputOnly(component)) {
      return;
    }

    super.decode(facesContext, component);
    String actionId = ComponentUtil.findPage(component).getActionId();
    if (actionId != null
        && actionId.equals(component.getClientId(facesContext) + CHANGE_BUTTON)){
      boolean state
          = ComponentUtil.getBooleanAttribute(component, ATTR_STATE_PREVIEW);
      component.getAttributes().put(TobagoConstants.ATTR_STATE_PREVIEW,
          new Boolean(! state));
      facesContext.renderResponse();

    }
    ((EditableValueHolder)component).setValid(true);
  }

  public static String contentToHtml(String content) {
    try {
      return WikiParser.toHtml(content);
    } catch (Exception e) {
      LOG.error("failed to parser wiki markup", e);
    }
    return content;
  }

  public void encodeEndTobago(FacesContext facesContext,
      UIComponent uiComponent) throws IOException {

    UIInput component = (UIInput) uiComponent;

    HtmlRendererUtil.createHeaderAndBodyStyles(facesContext, component);


    boolean previewState
        = ComponentUtil.getBooleanAttribute(component, ATTR_STATE_PREVIEW);
    // fixme: remove this when i18n is ok

    String clientId = component.getClientId(facesContext);

    ResponseWriter writer = facesContext.getResponseWriter();

    String classes
        = (String) component.getAttributes().get(TobagoConstants.ATTR_STYLE_CLASS);

    writer.startElement("div", component);
    writer.writeAttribute("class", classes + " tobago-richTextEditor-container", null);
    writer.writeAttribute("style", null, TobagoConstants.ATTR_STYLE);
    // class, stly.width, style.height

    UIComponent toolbar = component.getFacet(FACET_TOOL_BAR);
    if (toolbar == null) {
      toolbar = createToolbar(facesContext, component);
    }

    facesContext.getExternalContext().getRequestMap().put(
        "tobagoRichtextPreviewState", previewState ? Boolean.TRUE : Boolean.FALSE);

    HtmlRendererUtil.encodeHtml(facesContext, toolbar);
//    renderToolBar(facesContext, writer, component);

    String content = getCurrentValue(facesContext, component);

    if (previewState) {
      writer.startElement("input", component);
      writer.writeAttribute("type", "hidden", null);
      writer.writeAttribute("name", clientId, null);
      writer.writeAttribute("value", content, null);
      writer.endElement("input");

      writer.startElement("div", component);
      writer.writeAttribute("class", classes + " tobago-richTextEditor-body", null);
      writer.writeAttribute("id", clientId, null);

      writer.writeAttribute("style", null, TobagoConstants.ATTR_STYLE_BODY);

      writer.write(RichTextEditorRenderer.contentToHtml(content));

      writer.endElement("div");
    }
    else {
      writer.startElement("textarea", component);
      writer.writeAttribute("class", classes + " tobago-richTextEditor-body", null);
      writer.writeAttribute("name", clientId, null);
      writer.writeAttribute("id", clientId, null);
      writer.writeAttribute("style", null, TobagoConstants.ATTR_STYLE_BODY);
      String onchange = HtmlUtils.generateOnchange(component, facesContext);
      if (null != onchange) {
        writer.writeAttribute("onchange", onchange, null);
      }

      if (content != null) {
        writer.writeText(content, null);
      }

      writer.endElement("textarea");
    }
    writer.endElement("div");
  }

  private UIComponent createToolbar(FacesContext facesContext, UIInput component) {
    UIPanel toolbar = (UIPanel) ComponentUtil.createComponent(
        facesContext, UIPanel.COMPONENT_TYPE, RENDERER_TYPE_TOOL_BAR);
    String clientId = component.getClientId(facesContext);

    component.getFacets().put(FACET_TOOL_BAR, toolbar);
    toolbar.getAttributes().put(ATTR_ICON_SIZE, ToolBarTag.ICON_SMALL);
    toolbar.getAttributes().put(ATTR_LABEL_POSITION, ToolBarTag.LABEL_OFF);

    UICommand //command = (UICommand) ComponentUtil.createComponent(
//        facesContext, UICommand.COMPONENT_TYPE, RENDERER_TYPE_MENUCOMMAND);
//    toolbar.getChildren().add(command);



    command = (UICommand) ComponentUtil.createComponent(
        facesContext, UICommand.COMPONENT_TYPE, RENDERER_TYPE_MENUCOMMAND);
    toolbar.getChildren().add(command);
    command.getAttributes().put(ATTR_TYPE, COMMAND_TYPE_SCRIPT);
    command.getAttributes().put(ATTR_COMMAND_TYPE, ToolBarSelectBooleanTag.COMMAND_TYPE);
    command.getAttributes().put(ATTR_IMAGE, "image/tobago-richtext-edit.gif");
    command.setValueBinding(ATTR_DISABLED, ComponentUtil.createValueBinding("#{! tobagoRichtextPreviewState}", null));
    command.setValueBinding(ATTR_VALUE, ComponentUtil.createValueBinding("#{!tobagoRichtextPreviewState}", null));

    String title = ResourceManagerUtil.getProperty(
        facesContext, "tobago", "tobago.richtexteditor.edit.title");
    command.getAttributes().put(ATTR_TIP, title);

    String onClick = "submitAction('"
        + ComponentUtil.findPage(component).getFormId(facesContext) + "', '"
        + clientId + RichTextEditorRenderer.CHANGE_BUTTON + "')";
    command.getAttributes().put(ATTR_ACTION_STRING, onClick);




    command = (UICommand) ComponentUtil.createComponent(
        facesContext, UICommand.COMPONENT_TYPE, RENDERER_TYPE_MENUCOMMAND);
    toolbar.getChildren().add(command);
    command.getAttributes().put(ATTR_TYPE, COMMAND_TYPE_SCRIPT);
    command.getAttributes().put(ATTR_COMMAND_TYPE, ToolBarSelectBooleanTag.COMMAND_TYPE);
    command.getAttributes().put(ATTR_IMAGE, "image/tobago-richtext-preview.gif");
    command.setValueBinding(ATTR_DISABLED, ComponentUtil.createValueBinding("#{tobagoRichtextPreviewState}", null));
    command.setValueBinding(ATTR_VALUE, ComponentUtil.createValueBinding("#{tobagoRichtextPreviewState}", null));

    title = ResourceManagerUtil.getProperty(
        facesContext, "tobago", "tobago.richtexteditor.preview.title");
    command.getAttributes().put(ATTR_TIP, title);

    command.getAttributes().put(ATTR_ACTION_STRING, onClick);




    return toolbar;
  }

  private void createToolbarButton(FacesContext facesContext,
      UIInput component, ResponseWriter writer,
      String command, boolean enabled, String onClick)
      throws IOException {

    String onMouseArgs = "this, 'tobago-richTextEditor-toolbar-button-hover'";
    String onMouseOver = "addCssClass(" + onMouseArgs + ")";
    String onMouseOut = "removeCssClass(" + onMouseArgs + ")";

    String title = ResourceManagerUtil.getProperty(
        facesContext, "tobago", "tobago.richtexteditor." + command + ".title");
    writer.startElement("span", component);
    String buttonStyle = "tobago-richTextEditor-toolbar-button-span"
        + (enabled ? "-enabled" : "-disabled");
    writer.writeAttribute("class", buttonStyle, null);
    writer.writeAttribute("onclick", onClick, null);
    if (enabled) {
      writer.writeAttribute("onmouseover", onMouseOver, null);
      writer.writeAttribute("onmouseout", onMouseOut, null);
    }
    writer.writeAttribute("unselectable", "on", null);
    writer.writeAttribute("title", title, null);

    UIComponent image = component.getFacet("toolbarImage-" + command);

    if (image == null) {
      Application application = facesContext.getApplication();
      image = application.createComponent(UIGraphic.COMPONENT_TYPE);
      image.getAttributes().put(TobagoConstants.ATTR_STYLE, "vertical-align: bottom;");
      // image needs to be in component tree when rendering
      component.getFacets().put("toolbarImage-" + command, image);
    }
    image.getAttributes().put(TobagoConstants.ATTR_VALUE,
        "tobago-richText-" + command + ".gif");
    HtmlRendererUtil.encodeHtml(facesContext, image);

    writer.startElement("span", null);
    writer.writeAttribute("class", "tobago-richTextEditor-toolbar-button-label", null);
    String label = ResourceManagerUtil.getProperty(
        facesContext, "tobago", "tobago.richtexteditor." + command + ".label");
    writer.writeText(label, null);
    writer.endElement("span");
    writer.endElement("span");
  }
// ///////////////////////////////////////////// bean getter + setter

}
