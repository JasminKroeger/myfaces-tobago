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
    console.info("Tobago.Select2.init");                                    // @DEV_ONLY
    Tobago.Utils.selectWithJQuery(elements, "[data-tobago-select2]")
        .each( function () {
          var element = jQuery(this);
          var options = element.data("tobago-select2");
          var extend = element.data("tobago-select2-extend");
          var select2Options = jQuery.extend({}, options, extend);

          if (element.hasClass("tobago-selectManyBox")) {
            select2Options.containerCss = {height: element.data("tobago-style").height};
          }
          console.info("Select2.init" + element.attr("id") + " with data: " // @DEV_ONLY
              + JSON.stringify(select2Options));                            // @DEV_ONLY
          if (typeof select2Options.tokenizer === "string" && typeof eval(select2Options.tokenizer) === "function") {
            console.info("select2Options.tokenizer: " + typeof select2Options.tokenizer);
            console.info("select2Options.tokenizer: " + typeof eval(select2Options.tokenizer));
            select2Options.tokenizer = eval(select2Options.tokenizer)
          }
          var commands = element.data("tobago-commands");

          if (commands) {
            for (var name in commands) {
              if (name.indexOf("select2:") === 0) {
                var command = commands[name];
                var actionId = command.action;
                if (command.script) {
                  // not allowed with Content Security Policy (CSP)
                  var func = eval(command.script);
                  element.on("select2:select", func);
                } else if (command.partially) {
                  var partially = command.partially;
                  element.on("select2:select", function () {
                    if (actionId !== undefined) {
                      console.info("select2:select reloadComponent(" + partially + ", " + actionId + ")");
                      Tobago.reloadComponent(this, partially, actionId);
                    }
                  });
                } else {
                  var actionId = command.action;
                  element.on("select2:select", function () {
                    console.info("select2:select submitAction(" + actionId + ")");
                    Tobago.submitAction(this, actionId);
                  });
                }
              }
            }

          }
          element.select2(select2Options);
        });
  }

};
Tobago.registerListener(Tobago.Select2.init, Tobago.Phase.DOCUMENT_READY);
Tobago.registerListener(Tobago.Select2.init, Tobago.Phase.AFTER_UPDATE);
