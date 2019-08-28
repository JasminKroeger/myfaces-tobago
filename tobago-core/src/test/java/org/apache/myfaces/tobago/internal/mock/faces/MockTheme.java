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

package org.apache.myfaces.tobago.internal.mock.faces;

import org.apache.myfaces.tobago.context.Theme;
import org.apache.myfaces.tobago.context.ThemeImpl;
import org.apache.myfaces.tobago.context.ThemeScript;
import org.apache.myfaces.tobago.context.ThemeStyle;

import java.util.List;

public class MockTheme extends ThemeImpl {

  private String name;

  private String displayName;

  private List<Theme> fallbackThemeList;

  private boolean versioned;

  private String version;

  public MockTheme(final String name, final String displayName, final List<Theme> fallbackThemeList) {
    this.name = name;
    this.displayName = displayName;
    this.fallbackThemeList = fallbackThemeList;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public List<Theme> getFallbackList() {
    return fallbackThemeList;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

  @Override
  public ThemeScript[] getScriptResources(final boolean production) {
    return new ThemeScript[0];
  }

  @Override
  public ThemeStyle[] getStyleResources(final boolean production) {
    return new ThemeStyle[0];
  }

  @Override
  public boolean isVersioned() {
    return versioned;
  }

  @Override
  public void setVersioned(final boolean versioned) {
    this.versioned = versioned;
  }

  @Override
  public String getVersion() {
    return version;
  }

  @Override
  public void setVersion(final String version) {
    this.version = version;
  }
}
