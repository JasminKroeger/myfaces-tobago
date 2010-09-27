package org.apache.myfaces.tobago.internal.mock.faces;

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

import org.apache.myfaces.test.base.junit4.AbstractJsfTestCase;
import org.apache.myfaces.test.mock.MockFacesContext;
import org.apache.myfaces.test.mock.MockHttpServletRequest;
import org.apache.myfaces.tobago.component.ComponentTypes;
import org.apache.myfaces.tobago.component.UIIn;
import org.apache.myfaces.tobago.component.UIOut;
import org.apache.myfaces.tobago.config.TobagoConfig;
import org.apache.myfaces.tobago.context.ClientProperties;
import org.apache.myfaces.tobago.context.Theme;
import org.junit.Before;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Abstract JUnit test case base class, which sets up the JavaServer Faces
 * mock object environment for a particular simulated request.
 * </p>
 * <p>
 * This is a port of the class AbstractJsfTestCase from myfaces-test12 to JUnit 4.
 * It also contains Tobago specifics.
 * </p>
 */

public abstract class AbstractTobagoTestBase extends AbstractJsfTestCase {

  /**
   * <p>Set up instance variables required by this test case.</p>
   */
  @Before
  public void setUp() throws Exception {

    super.setUp();

    // Tobago specific extensions

    TobagoConfig tobagoConfig = new TobagoConfig();
    Theme theme = new MockTheme("default", "Default Mock Theme", Collections.EMPTY_LIST);
    Theme one = new MockTheme("one", "Mock Theme One", Arrays.asList(theme));
    Map<String, Theme> availableThemes = new HashMap<String, Theme>();
    availableThemes.put(theme.getName(), theme);
    availableThemes.put(one.getName(), one);
    tobagoConfig.setAvailableThemes(availableThemes);
    tobagoConfig.resolveThemes();
    tobagoConfig.initProjectState(servletContext);
    servletContext.setAttribute(TobagoConfig.TOBAGO_CONFIG, tobagoConfig);

    final ClientProperties clientProperties = new ClientProperties();
    clientProperties.setTheme(one);
    session.setAttribute(ClientProperties.MANAGED_BEAN_NAME, clientProperties);

    // XXX is there a better way? Get it from Tobagos generated faces-config.xml?
    application.addComponent(ComponentTypes.IN, UIIn.class.getName());
    application.addComponent(ComponentTypes.OUT, UIOut.class.getName());
    application.addComponent("javax.faces.ViewRoot", "org.apache.myfaces.tobago.component.UIViewRoot");
    application.addComponent("javax.faces.Command", "javax.faces.component.UICommand");
    application.addComponent("org.apache.myfaces.tobago.Command", "org.apache.myfaces.tobago.component.UICommand");
    application.addComponent("org.apache.myfaces.tobago.Link", "org.apache.myfaces.tobago.component.UILink");
    application.addComponent("org.apache.myfaces.tobago.Button", "org.apache.myfaces.tobago.component.UIButton");

  }

  public MockFacesContext getFacesContext() {
    return facesContext;
  }

  public MockHttpServletRequest getRequest() {
    return request;
  }
}
