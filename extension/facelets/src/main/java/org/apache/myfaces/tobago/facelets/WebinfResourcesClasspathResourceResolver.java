package org.apache.myfaces.tobago.facelets;

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

import com.sun.facelets.impl.DefaultResourceResolver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URL;

/**
 * Provides facelets from the directory "META-INF/resources/" of any jar file in the classpath.
 * With the Servlet 3.0 specification this is no longer needed.
 * <p/>
 * To configure this ResourceResolver put this code into the web.xml file:
 * <pre>
 *  &lt;context-param&gt;
 *    &lt;param-name&gt;facelets.RESOURCE_RESOLVER&lt;/param-name&gt;
 *    &lt;param-value&gt;org.apache.myfaces.tobago.facelets.WebinfResourcesClasspathResourceResolver&lt;/param-value&gt;
 *  &lt;/context-param&gt;
 * </pre>
 * <p/>
 * User: lofwyr
 * Date: 12.05.2009
 * Time: 12:25:31
 */
public class WebinfResourcesClasspathResourceResolver extends DefaultResourceResolver {

  private static final Log LOG = LogFactory.getLog(WebinfResourcesClasspathResourceResolver.class);

  public WebinfResourcesClasspathResourceResolver() {
    if (LOG.isDebugEnabled()) {
      LOG.debug("WebinfResourcesClasspathResourceResolver is configured to resolve resoureces.");
    }
  }

  public URL resolveUrl(String path) {
    URL url = super.resolveUrl(path);
    if (LOG.isDebugEnabled()) {
      LOG.debug("default   url='" + url + "'");
    }
    if (url == null) {
      if (path.startsWith("/")) {
        path = path.substring(1);
      }
      path = "META-INF/resources/" + path;

      url = Thread.currentThread().getContextClassLoader().getResource(path);
      if (LOG.isDebugEnabled()) {
        LOG.debug("classpath url='" + url + "'");
      }
    }
    return url;
  }
}
