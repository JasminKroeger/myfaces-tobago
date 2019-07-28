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

Tobago.Select2 = {

  init: function (elements) {
    console.info("Tobago.Select2.init"); // @DEV_ONLY
    Tobago.Utils.selectWithJQuery(elements, "[data-tobago-select2]")
        .each( function () {
          var element = jQuery(this);
          var options = element.data("tobago-select2");
          console.info("typeof options: " + typeof options);
          var value = {
            minimumResultsForSearch: Infinity,
            ts: "hallo",
            tn: 22
          };
          console.info("XXXX : " + JSON.stringify(value));
          options.containerCss = element.data("tobago-style");
          options.dropdownCss = {
            left: options.containerCss.left,
            top: options.containerCss.top,
            position: "absolute"
          };
          console.info("Select2.init" + element.attr("id") + " with data: " + JSON.stringify(options)); // @DEV_ONLY
          element.select2(options);
        });
  }

};
Tobago.registerListener(Tobago.Select2.init, Tobago.Phase.DOCUMENT_READY);
Tobago.registerListener(Tobago.Select2.init, Tobago.Phase.AFTER_UPDATE);
