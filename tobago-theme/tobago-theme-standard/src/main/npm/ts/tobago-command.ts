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

Tobago4.Command = {};

Tobago4.Command.initEnter = function(elements) {
  elements = elements.jQuery ? elements : jQuery(elements); // fixme jQuery -> ES5
  var page = Tobago4.Utils.selectWithJQuery(elements, ".tobago-page");
  page.keypress(function (event) {
    var code = event.which;
    if (code === 0) {
      code = event.keyCode;
    }
    if (code === 13) {
      var target = event.target;
      if (target.tagName === "A" || target.tagName === "BUTTON") {
        return;
      }
      if (target.tagName === "TEXTAREA") {
        if (!event.metaKey && !event.ctrlKey) {
          return;
        }
      }
      var id = target.name ? target.name : target.id;
      while (id != null) {
        var command = jQuery("[data-tobago-default='" + id + "']");
        if (command.length > 0) {
          command.click();
          break;
        }
        id = Tobago4.Utils.getNamingContainerId(id);
      }
      return false;
    }
  });
};

Tobago.Listener.register(Tobago4.Command.initEnter, Tobago.Phase.DOCUMENT_READY);

/** @deprecated since 5.0.0 */
Tobago4.initCommand = function(element) {
  Tobago4.Command.init(element);
};

Tobago4.Command.init = function(element) {
  // command is jQuery object
  // setupInputFacetCommand
  const commands = element.data("tobago-commands");

  var normalEvents = []; // todo: find a better way to do that in JS
  if (commands.click) {
    normalEvents.push({event: "click", command: commands.click});
  }
  if (commands.dblclick) {
    normalEvents.push({event: "dblclick", command: commands.dblclick});
  }
  if (commands.focus) {
    normalEvents.push({event: "focus", command: commands.focus});
  }
  if (commands.blur) {
    normalEvents.push({event: "blur", command: commands.blur});
  }

  for (var i in normalEvents) {

    element.on(normalEvents[i].event, {command: normalEvents[i].command}, function (event) {
      var command = event.data.command;
      var confirmation = command.confirmation;
      if (confirmation != null) {
        if (!confirm(confirmation)) {
          event.preventDefault();
          return;
        }
      }
      var collapse = command.collapse;
      if (collapse) {
        Tobago4.Collapse.execute(collapse);
      }

      if (command.omit !== true) {
        var popup = command.popup;
        if (popup && popup.command === "close" && popup.immediate) {
          Tobago4.Popup.close(this);
        } else {
          var action = command.action ? command.action : jQuery(this).attr("id");
          if (command.execute || command.render) {
            Tobago4.Command.preparePartialOverlay(command);
            jsf.ajax.request(
                action,
                event,
                {
                  "javax.faces.behavior.event": event.type,
                  execute: command.execute,
                  render: command.render
                });
          } else {
            Tobago4.submitAction(this, action, command);
          }
          if (popup && popup.command === "close") {
            Tobago4.Popup.close(this);
          }
        }
      }
    });
  }
  if (commands.change) {
    element.change(function(event) {
      if (commands.change.execute || commands.change.render) {
        jsf.ajax.request(
            jQuery(this).attr("name"),
            event,
            {
              "javax.faces.behavior.event": "change",
              execute: commands.change.execute,
              render: commands.change.render
            });
      } else {
        Tobago4.submitAction(this, commands.change.action, commands.change);
      }
    });
  }
  if (commands.complete) {
    if (element.val() >= parseFloat(element.attr("max"))) {
      if (commands.complete.execute || commands.complete.render) {
        jsf.ajax.request(
            jQuery(this).attr("id"),
            null,
            {
              "javax.faces.behavior.event": "complete",
              execute: commands.complete.execute,
              render: commands.complete.render
            });
      } else {
        Tobago4.submitAction(this, commands.complete.action, commands.complete);
      }
    }
  }
  if (commands.load) {
    setTimeout(function() {
          Tobago4.submitAction(this, commands.load.action, commands.load);
        },
        commands.load.delay || 100);
  }
  if (commands.resize) {
    jQuery(window).resize(function() {
      console.debug("window resize event: " + commands.resize);
      Tobago4.submitAction(this, commands.resize.action, commands.resize);
    });
  }
};

Tobago4.Command.preparePartialOverlay = function (command) {
  if (command.transition === undefined || command.transition == null || command.transition) {
    console.debug("[tobago-command] render: '" + command.render + "'");
    if (command.render) {
      let partialIds = command.render.split(" ");
      for (let i = 0; i < partialIds.length; i++) {
        new Tobago.Overlay(document.getElementById(partialIds[i]), true);
      }
    }
  }
};
