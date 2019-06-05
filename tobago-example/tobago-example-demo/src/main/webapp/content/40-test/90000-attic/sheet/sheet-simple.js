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

function test_colorize() {
  jQuery("td>span,th>span").css("background-color", "white");
  jQuery("td:odd").css("background-color", "#ffbbff");
  jQuery("td:even").css("background-color", "#bbffbb");
  jQuery("th:odd").css("background-color", "#dd99dd");
  jQuery("th:even").css("background-color", "#99dd99");
}

Listener.register(test_colorize, Phase.DOCUMENT_READY);
Listener.register(test_colorize, Phase.AFTER_UPDATE);
