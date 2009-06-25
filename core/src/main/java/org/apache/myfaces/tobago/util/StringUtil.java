package org.apache.myfaces.tobago.util;

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

import java.util.List;

/**
 * @deprecated Please use StringUtils
 * @see StringUtils
 */
@Deprecated
public class StringUtil {

  private StringUtil() {
  }

  @Deprecated
  public static String firstToUpperCase(String string) {
    return StringUtils.firstToUpperCase(string);
  }

  @Deprecated
  public static List<Integer> parseIntegerList(String integerList)
      throws NumberFormatException {
   return StringUtils.parseIntegerList(integerList);
  }

  @Deprecated
  public static <T> String toString(List<T> list) {
    return StringUtils.toString(list);
  }

  @Deprecated
  public static String escapeAccessKeyIndicator(String label) {
    return StringUtils.escapeAccessKeyIndicator(label);
  }

}
