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

package org.apache.myfaces.tobago.model;


import org.apache.myfaces.tobago.component.UISelectOneChoice;

public class Select2Options {

  private Boolean tags;
  private String[] tokenSeparators;
  private Boolean allowClear;
  private String language;
  private String matcher;
  private Integer maximumInputLength;
  private Integer minimumInputLength;
  private Integer maximumSelectionLength;
  private Integer minimumResultsForSearch;
  private String placeholder;

  public static Select2Options of(UISelectOneChoice select) {
    boolean renderSelect2 = select.isSelect2();
    Select2Options options = new Select2Options();
    if (select.isAllowCustomSet()) {
      options.setTags(select.isAllowCustom());
    }
    if (renderSelect2 || options.hasAnyOption()) {
      return options;
    } else {
      return null;
    }
  }

  private boolean hasAnyOption() {
    return tags != null
           || tokenSeparators != null
           || allowClear != null
           || matcher != null
           || maximumInputLength != null
           || minimumInputLength != null
           || maximumSelectionLength != null
           || minimumResultsForSearch != null
           || placeholder != null;
  }

  public boolean isTags() {
    return tags;
  }

  public void setTags(boolean tags) {
    this.tags = tags;
  }

  public String[] getTokenSeparators() {
    return tokenSeparators;
  }

  public void setTokenSeparators(String[] tokenSeparators) {
    this.tokenSeparators = tokenSeparators;
  }

  public boolean isAllowClear() {
    return allowClear;
  }

  public void setAllowClear(boolean allowClear) {
    this.allowClear = allowClear;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getMatcher() {
    return matcher;
  }

  public void setMatcher(String matcher) {
    this.matcher = matcher;
  }

  public int getMaximumInputLength() {
    return maximumInputLength;
  }

  public void setMaximumInputLength(int maximumInputLength) {
    this.maximumInputLength = maximumInputLength;
  }

  public int getMinimumInputLength() {
    return minimumInputLength;
  }

  public void setMinimumInputLength(int minimumInputLength) {
    this.minimumInputLength = minimumInputLength;
  }

  public int getMaximumSelectionLength() {
    return maximumSelectionLength;
  }

  public void setMaximumSelectionLength(int maximumSelectionLength) {
    this.maximumSelectionLength = maximumSelectionLength;
  }

  public int getMinimumResultsForSearch() {
    return minimumResultsForSearch;
  }

  public void setMinimumResultsForSearch(int minimumResultsForSearch) {
    this.minimumResultsForSearch = minimumResultsForSearch;
  }

  public String getPlaceholder() {
    return placeholder;
  }

  public void setPlaceholder(String placeholder) {
    this.placeholder = placeholder;
  }
}
