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

import {jQueryFrameFn} from "/script/tobago-test.js";
import {TobagoTestTool} from "/tobago/test/tobago-test-tool.js";

QUnit.test("tc:button", function (assert) {
  var eventNames = ["click", "dblclick", "focus", "blur"];
  var eventComponentFn = jQueryFrameFn("#page\\:mainForm\\:buttonevent");
  var ajaxComponentFn = jQueryFrameFn("#page\\:mainForm\\:buttonajax");
  testEvent(assert, "button", eventNames, eventComponentFn, ajaxComponentFn, null);
});

QUnit.test("tc:in", function (assert) {
  var eventNames = ["change", "click", "dblclick", "focus", "blur"];
  var eventComponentFn = jQueryFrameFn("#page\\:mainForm\\:inevent\\:\\:field");
  var ajaxComponentFn = jQueryFrameFn("#page\\:mainForm\\:inajax\\:\\:field");
  var changeValue = function (componentFn) {
    var oldValue = componentFn().val();
    var newValue = "hello";
    if (oldValue === "hello") {
      newValue = "hello there!"
    }
    componentFn().val(newValue);
  };
  testEvent(assert, "in", eventNames, eventComponentFn, ajaxComponentFn, changeValue);
});

QUnit.test("tc:row", function (assert) {
  var eventNames = ["click", "dblclick"];
  var eventComponentFn = jQueryFrameFn("#page\\:mainForm\\:sheetevent\\:0\\:selectPlanet");
  var ajaxComponentFn = jQueryFrameFn("#page\\:mainForm\\:sheetajax\\:0\\:selectPlanet");
  testEvent(assert, "row", eventNames, eventComponentFn, ajaxComponentFn, null);
});

QUnit.test("tc:selectBooleanCheckbox", function (assert) {
  var eventNames = ["change", "click", "dblclick", "focus", "blur"];
  var eventComponentFn = jQueryFrameFn("#page\\:mainForm\\:selectBooleanCheckboxevent\\:\\:field");
  var ajaxComponentFn = jQueryFrameFn("#page\\:mainForm\\:selectBooleanCheckboxajax\\:\\:field");
  var changeValue = function (componentFn) {
    var currentEvent = jQueryFrame("#page\\:mainForm\\:outEventName span").text();
    if (currentEvent !== "click") {
      componentFn().prop("checked", !componentFn().prop("checked"));
    }
  };
  testEvent(assert, "selectBooleanCheckbox", eventNames, eventComponentFn, ajaxComponentFn, changeValue);
});

QUnit.test("tc:textarea", function (assert) {
  var eventNames = ["change", "click", "dblclick", "focus", "blur"];
  var eventComponentFn = jQueryFrameFn("#page\\:mainForm\\:textareaevent\\:\\:field");
  var ajaxComponentFn = jQueryFrameFn("#page\\:mainForm\\:textareaajax\\:\\:field");
  var changeValue = function (componentFn) {
    var oldValue = componentFn().val();
    var newValue = "hello";
    if (oldValue === "hello") {
      newValue = "hello there!"
    }
    componentFn().val(newValue);
  };
  testEvent(assert, "textarea", eventNames, eventComponentFn, ajaxComponentFn, changeValue);
});

function testEvent(assert, componentName, eventNames, eventComponentFn, ajaxComponentFn, changeValueFunc) {
  var oldActionCount;
  var oldActionListenerCount;
  var oldAjaxListenerCount;
  var oldValueChangeListenerCount;
  var oldTimestamp;

  var TTT = new TobagoTestTool(assert);
  for (var i = 0; i < eventNames.length; i++) {
    // need 'let' here, otherwise all tests are executed with last eventName in array
    // also work in IE11
    let eventName = eventNames[i];

    TTT.action(function () {
      activateComponent(componentName, eventName);
    });
    TTT.waitForResponse();
    TTT.action(function () {
      oldActionCount = getActionCount();
      oldActionListenerCount = getActionListenerCount();
      oldAjaxListenerCount = getAjaxListenerCount();
      oldValueChangeListenerCount = getValueChangeListenerCount();
      oldTimestamp = getTimestamp();

      if (changeValueFunc !== null) {
        changeValueFunc(eventComponentFn);
      }
      eventComponentFn().trigger(eventName);
    });
    TTT.waitForResponse();
    TTT.asserts(5, function () {
      assert.equal(getActionCount(), oldActionCount + 1, eventName + " - tc:event - action");
      assert.equal(getActionListenerCount(), oldActionListenerCount + 1, eventName + " - tc:event - actionListener");
      assert.equal(getAjaxListenerCount(), oldAjaxListenerCount, eventName + " - tc:event - ajaxListener");
      if (changeValueFunc !== null) {
        assert.equal(getValueChangeListenerCount(), oldValueChangeListenerCount + 1,
            eventName + " - tc:event - valueChangeListener");
      } else {
        assert.equal(getValueChangeListenerCount(), oldValueChangeListenerCount,
            eventName + " - tc:event - valueChangeListener");
      }
      assert.ok(getTimestamp() > oldTimestamp, eventName + " - tc:event - timestamp");
    });
    TTT.action(function () {
      oldActionCount = getActionCount();
      oldActionListenerCount = getActionListenerCount();
      oldAjaxListenerCount = getAjaxListenerCount();
      oldValueChangeListenerCount = getValueChangeListenerCount();
      oldTimestamp = getTimestamp();

      if (changeValueFunc !== null) {
        changeValueFunc(ajaxComponentFn);
      }
      ajaxComponentFn().trigger(eventName);
    });
    TTT.waitForResponse();
    TTT.asserts(5, function () {
      assert.equal(getActionCount(), oldActionCount, eventName + " - f:ajax - action");
      assert.equal(getActionListenerCount(), oldActionListenerCount, eventName + " - f:ajax - actionListener");
      assert.equal(getAjaxListenerCount(), oldAjaxListenerCount + 1, eventName + " - f:ajax - ajaxListener");
      if (changeValueFunc !== null) {
        assert.equal(getValueChangeListenerCount(), oldValueChangeListenerCount + 1,
            eventName + " - f:ajax - valueChangeListener");
      } else {
        assert.equal(getValueChangeListenerCount(), oldValueChangeListenerCount,
            eventName + " - f:ajax - valueChangeListener");
      }
      assert.ok(getTimestamp() > oldTimestamp, eventName + " - f:ajax - timestamp");
    });
  }
  TTT.startTest();
}

function activateComponent(componentName, eventName) {
  jQueryFrame("#page\\:mainForm\\:componentTable .tobago-sheet-row").each(function () {
    if (jQuery(this).find("td").eq(0).find(".tobago-out").text() === componentName) {
      jQuery(this).find("button").each(function () {
        var id = jQuery(this).attr("id");
        if (id !== undefined && id.indexOf(eventName + "Behavior") >= 0) {
          this.click();
        }
      });
    }
  });
}

function getActionCount() {
  return parseInt(jQueryFrame("#page\\:mainForm\\:inAction\\:\\:field").val());
}

function getActionListenerCount() {
  return parseInt(jQueryFrame("#page\\:mainForm\\:inActionListener\\:\\:field").val());
}

function getAjaxListenerCount() {
  return parseInt(jQueryFrame("#page\\:mainForm\\:inAjaxListener\\:\\:field").val());
}

function getValueChangeListenerCount() {
  return parseInt(jQueryFrame("#page\\:mainForm\\:inValueChangeListener\\:\\:field").val());
}

function getTimestamp() {
  return parseInt(jQueryFrame("#page\\:mainForm\\:inTimestamp\\:\\:field").val());
}
