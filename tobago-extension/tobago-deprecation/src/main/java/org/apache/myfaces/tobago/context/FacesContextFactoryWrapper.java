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

package org.apache.myfaces.tobago.context;

import javax.faces.context.FacesContextFactory;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.FacesException;

/**
 * @deprecated since 2.0.0
 */
@Deprecated
public class FacesContextFactoryWrapper extends FacesContextFactory {
  private FacesContextFactory facesContextFactory;

  public FacesContextFactoryWrapper(final FacesContextFactory facesContextFactory) {
    this.facesContextFactory = facesContextFactory;
  }

  public FacesContext getFacesContext(
      final Object context, final Object request, final Object response, final Lifecycle lifecycle)
      throws FacesException {
    return facesContextFactory.getFacesContext(context, request, response, lifecycle);
  }

  public final FacesContextFactory getFacesContextFactory() {
    return facesContextFactory;
  }
}