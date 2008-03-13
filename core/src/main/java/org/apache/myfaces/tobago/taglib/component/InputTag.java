package org.apache.myfaces.tobago.taglib.component;

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
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_FOCUS;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_IMMEDIATE;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_ONCHANGE;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_TAB_INDEX;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_TIP;
import org.apache.myfaces.tobago.component.ComponentUtil;
import org.apache.myfaces.tobago.internal.taglib.TagUtils;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;

public abstract class InputTag extends BeanTag implements InputTagDeclaration {
  private static final Log LOG = LogFactory.getLog(InputTag.class);

  private String onchange;
  private String focus;
  private String tip;
  private String validator;
  private String valueChangeListener;
  private String tabIndex;
  private String immediate;

  public void release() {
    super.release();
    this.onchange = null;
    this.focus = null;
    tip = null;
    validator = null;
    valueChangeListener = null;
    tabIndex = null;
    immediate = null;
  }

  protected void setProperties(UIComponent component) {
    super.setProperties(component);
    TagUtils.setStringProperty(component, ATTR_ONCHANGE, onchange);
    TagUtils.setBooleanProperty(component, ATTR_FOCUS, focus);
    TagUtils.setStringProperty(component, ATTR_TIP, tip);
    TagUtils.setIntegerProperty(component, ATTR_TAB_INDEX, tabIndex);
    if (component instanceof EditableValueHolder) {
      EditableValueHolder editableValueHolder = (EditableValueHolder) component;
      ComponentUtil.setValidator(editableValueHolder, validator);
      ComponentUtil.setValueChangeListener(editableValueHolder, valueChangeListener);
      TagUtils.setBooleanProperty(component, ATTR_IMMEDIATE, immediate);
    }
  }

  public String getOnchange() {
    return onchange;
  }

  public void setOnchange(String onchange) {
    this.onchange = onchange;
  }

  public String getFocus() {
    return focus;
  }

  public void setFocus(String focus) {
    this.focus = focus;
  }

  public String getAccessKey() {
    return null;
  }

  public void setAccessKey(String accessKey) {
    LOG.warn("Attibute 'accessKey' is deprecated, "
        + "and will removed soon!");
  }

  public String getLabelWithAccessKey() {
    return null;
  }

  public void setLabelWithAccessKey(String labelWithAccessKey) {
    LOG.warn("Attibute 'labelWithAccessKey' is deprecated, "
        + "and will removed soon! Please use 'label' instead.");
    setLabel(labelWithAccessKey);
  }

  public String getTip() {
    return tip;
  }

  public void setTip(String tip) {
    this.tip = tip;
  }

  public String getValidator() {
    return validator;
  }

  public void setValidator(String validator) {
    this.validator = validator;
  }

  public String getValueChangeListener() {
    return valueChangeListener;
  }

  public void setValueChangeListener(String valueChangeListener) {
    this.valueChangeListener = valueChangeListener;
  }

  public String getTabIndex() {
    return tabIndex;
  }

  public void setTabIndex(String tabIndex) {
    this.tabIndex = tabIndex;
  }

  public String getImmediate() {
    return immediate;
  }

  public void setImmediate(String immediate) {
    this.immediate = immediate;
  }
}

