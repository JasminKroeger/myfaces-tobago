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

import {jQueryFrameFn, testFrameQuerySelectorFn} from "/script/tobago-test.js";
import {TobagoTestTool} from "/tobago/test/tobago-test-tool.js";

QUnit.test("On click with ajax", function (assert) {
  var oneClickAjaxFn = testFrameQuerySelectorFn("#page\\:mainForm\\:changeExample\\:\\:0");
  var venusFn = jQueryFrameFn("#page\\:mainForm\\:s1\\:2\\:sample0");
  var jupiterFn = jQueryFrameFn("#page\\:mainForm\\:s1\\:5\\:sample0");
  var saturnFn = jQueryFrameFn("#page\\:mainForm\\:s1\\:6\\:sample0");
  var namefieldFn = jQueryFrameFn("#page\\:mainForm\\:name\\:\\:field");

  var TTT = new TobagoTestTool(assert);
  TTT.action(function () {
    oneClickAjaxFn().checked = true;
    oneClickAjaxFn().dispatchEvent(new Event('change'));
  });
  TTT.waitForResponse();
  TTT.asserts(3, function () {
    assert.equal(venusFn().length, 1);
    assert.equal(jupiterFn().length, 1);
    assert.equal(saturnFn().length, 1);
  });
  TTT.action(function () {
    venusFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(1, function () {
    assert.equal(namefieldFn().val(), "Venus");
  });
  TTT.action(function () {
    jupiterFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(1, function () {
    assert.equal(namefieldFn().val(), "Jupiter");
  });
  TTT.action(function () {
    saturnFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(1, function () {
    assert.equal(namefieldFn().val(), "Saturn");
  });
  TTT.startTest();
});

QUnit.test("On click with full request", function (assert) {
  var oneClickFullRequestFn = testFrameQuerySelectorFn("#page\\:mainForm\\:changeExample\\:\\:1");
  var venusFn = jQueryFrameFn("#page\\:mainForm\\:s1\\:2\\:sample1");
  var jupiterFn = jQueryFrameFn("#page\\:mainForm\\:s1\\:5\\:sample1");
  var saturnFn = jQueryFrameFn("#page\\:mainForm\\:s1\\:6\\:sample1");
  var namefieldFn = jQueryFrameFn("#page\\:mainForm\\:name\\:\\:field");

  var TTT = new TobagoTestTool(assert);
  TTT.action(function () {
    oneClickFullRequestFn().checked = true;
    oneClickFullRequestFn().dispatchEvent(new Event('change'));
  });
  TTT.waitForResponse();
  TTT.asserts(3, function () {
    assert.equal(venusFn().length, 1);
    assert.equal(jupiterFn().length, 1);
    assert.equal(saturnFn().length, 1);
  });
  TTT.action(function () {
    venusFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(1, function () {
    assert.equal(namefieldFn().val(), "Venus");
  });
  TTT.action(function () {
    jupiterFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(1, function () {
    assert.equal(namefieldFn().val(), "Jupiter");
  });
  TTT.action(function () {
    saturnFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(1, function () {
    assert.equal(namefieldFn().val(), "Saturn");
  });
  TTT.startTest();
});

QUnit.test("On double click with full request", function (assert) {
  var doubleClickFullRequestFn = testFrameQuerySelectorFn("#page\\:mainForm\\:changeExample\\:\\:2");
  var venusFn = jQueryFrameFn("#page\\:mainForm\\:s1\\:2\\:sample2");
  var jupiterFn = jQueryFrameFn("#page\\:mainForm\\:s1\\:5\\:sample2");
  var saturnFn = jQueryFrameFn("#page\\:mainForm\\:s1\\:6\\:sample2");
  var namefieldFn = jQueryFrameFn("#page\\:mainForm\\:name\\:\\:field");

  var TTT = new TobagoTestTool(assert);
  TTT.action(function () {
    doubleClickFullRequestFn().checked = true;
    doubleClickFullRequestFn().dispatchEvent(new Event('change'));
  });
  TTT.waitForResponse();
  TTT.asserts(3, function () {
    assert.equal(venusFn().length, 1);
    assert.equal(jupiterFn().length, 1);
    assert.equal(saturnFn().length, 1);
  });
  TTT.action(function () {
    venusFn().dblclick();
  });
  TTT.waitForResponse();
  TTT.asserts(1, function () {
    assert.equal(namefieldFn().val(), "Venus");
  });
  TTT.action(function () {
    jupiterFn().dblclick();
  });
  TTT.waitForResponse();
  TTT.asserts(1, function () {
    assert.equal(namefieldFn().val(), "Jupiter");
  });
  TTT.action(function () {
    saturnFn().dblclick();
  });
  TTT.waitForResponse();
  TTT.asserts(1, function () {
    assert.equal(namefieldFn().val(), "Saturn");
  });
  TTT.startTest();
});

QUnit.test("Open popup on click with ajax", function (assert) {
  var radioButtonFn = testFrameQuerySelectorFn("#page\\:mainForm\\:changeExample\\:\\:3");
  var venusFn = jQueryFrameFn("#page\\:mainForm\\:s1\\:2\\:sample3");
  var jupiterFn = jQueryFrameFn("#page\\:mainForm\\:s1\\:5\\:sample3");
  var saturnFn = jQueryFrameFn("#page\\:mainForm\\:s1\\:6\\:sample3");
  var popupFn = jQueryFrameFn("#page\\:mainForm\\:popup");
  var nameFn = jQueryFrameFn("#page\\:mainForm\\:popup\\:popupName\\:\\:field");
  var cancelFn = jQueryFrameFn("#page\\:mainForm\\:popup\\:cancel");

  var TTT = new TobagoTestTool(assert);
  TTT.action(function () {
    radioButtonFn().checked = true;
    radioButtonFn().dispatchEvent(new Event('change'));
  });
  TTT.waitForResponse();
  TTT.asserts(3, function () {
    assert.equal(venusFn().length, 1);
    assert.equal(jupiterFn().length, 1);
    assert.equal(saturnFn().length, 1);
  });
  TTT.action(function () {
    venusFn().click();
  });
  TTT.waitForResponse();
  TTT.waitMs(1000); // wait for animation
  TTT.asserts(2, function () {
    assert.ok(popupFn().hasClass("show"));
    assert.equal(nameFn().val(), "Venus");
  });
  TTT.action(function () {
    cancelFn().click();
  });
  TTT.waitMs(1000); // wait for animation
  TTT.asserts(1, function () {
    assert.notOk(popupFn().hasClass("show"));
  });
  TTT.action(function () {
    jupiterFn().click();
  });
  TTT.waitForResponse();
  TTT.waitMs(1000); // wait for animation
  TTT.asserts(2, function () {
    assert.ok(popupFn().hasClass("show"));
    assert.equal(nameFn().val(), "Jupiter");
  });
  TTT.action(function () {
    cancelFn().click();
  });
  TTT.waitMs(1000); // wait for animation
  TTT.asserts(1, function () {
    assert.notOk(popupFn().hasClass("show"));
  });
  TTT.action(function () {
    saturnFn().click();
  });
  TTT.waitForResponse();
  TTT.waitMs(1000); // wait for animation
  TTT.asserts(2, function () {
    assert.ok(popupFn().hasClass("show"));
    assert.equal(nameFn().val(), "Saturn");
  });
  TTT.action(function () {
    cancelFn().click();
  });
  TTT.waitMs(1000); // wait for animation
  TTT.asserts(1, function () {
    assert.notOk(popupFn().hasClass("show"));
  });
  TTT.startTest();
});
