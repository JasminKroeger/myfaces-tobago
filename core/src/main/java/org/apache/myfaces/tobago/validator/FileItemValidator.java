package org.apache.myfaces.tobago.validator;

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

import org.apache.myfaces.tobago.component.UIFileInput;
import org.apache.myfaces.tobago.util.MessageFactory;
import org.apache.myfaces.tobago.util.ContentType;
import org.apache.commons.fileupload.FileItem;

import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

/*
 * Date: Oct 30, 2006
 * Time: 9:59:13 PM
 */

/**
 * <p><strong>FileItemValidator</strong> is a {@link Validator} that checks
 * the FileItem in the value of the associated component.
 */

@org.apache.myfaces.tobago.apt.annotation.Validator(id = FileItemValidator.VALIDATOR_ID)
public class FileItemValidator implements Validator, StateHolder {
  public static final String VALIDATOR_ID = "org.apache.myfaces.tobago.FileItem";
  public static final String SIZE_LIMIT_MESSAGE_ID = "org.apache.myfaces.tobago.FileItemValidator.SIZE_LIMIT";
  public static final String CONTENT_TYPE_MESSAGE_ID = "org.apache.myfaces.tobago.FileItemValidator.CONTENT_TYPE";
  private Integer maxSize;
  private String contentType;
  private boolean transientValue;

  public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    if (value != null && component instanceof UIFileInput) {
      FileItem file = (FileItem) value;
      if (maxSize != null && file.getSize() > maxSize) {
        Object[] args = {maxSize, component.getId()};
        FacesMessage facesMessage = MessageFactory.createFacesMessage(context,
            SIZE_LIMIT_MESSAGE_ID, FacesMessage.SEVERITY_ERROR, args);
        throw new ValidatorException(facesMessage);
      }
      // Check only a valid file
      if (file.getSize() > 0 && contentType != null
          && !ContentType.valueOf(contentType).match(ContentType.valueOf(file.getContentType()))) {
        ContentType expectedContentType = ContentType.valueOf(contentType);
        Object[] args = {expectedContentType, component.getId()};
        FacesMessage facesMessage = MessageFactory.createFacesMessage(context,
            CONTENT_TYPE_MESSAGE_ID, FacesMessage.SEVERITY_ERROR, args);
        throw new ValidatorException(facesMessage);
      }
    }
  }

  public int getMaxSize() {
    return maxSize;
  }

  public void setMaxSize(int maxSize) {
    if (maxSize > 0) {
      this.maxSize = maxSize;
    }
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public Object saveState(FacesContext context) {
    Object[] values = new Object[2];
    values[0] = maxSize;
    values[1] = contentType;
    return values;
  }

  public void restoreState(FacesContext context, Object state) {
    Object[] values = (Object[]) state;
    maxSize = (Integer) values[0];
    contentType = (String) values[1];
  }

  public boolean isTransient() {
    return transientValue;
  }

  public void setTransient(boolean newTransientValue) {
    this.transientValue = newTransientValue;
  }
}
