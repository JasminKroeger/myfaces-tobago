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

QUnit.test("Accordion: Box 1: 'hide' to 'show' to 'hide'", function (assert) {
  var boxFn = jQueryFrameFn("#page\\:mainForm\\:accordionBox1");
  var showBoxFn = jQueryFrameFn("#page\\:mainForm\\:showBox1");
  var hideBoxFn = jQueryFrameFn("#page\\:mainForm\\:hideBox1");
  var boxBodyFn = jQueryFrameFn("#page\\:mainForm\\:accordionBox1 .card-body");

  var TTT = new TobagoTestTool(assert);
  TTT.asserts(3, function () {
    assert.equal(showBoxFn().length, 1);
    assert.equal(hideBoxFn().length, 0);
    assert.equal(boxBodyFn().text().trim().length, 0);
  });
  TTT.action(function () {
    showBoxFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(3, function () {
    assert.equal(showBoxFn().length, 0);
    assert.equal(hideBoxFn().length, 1);
    assert.notEqual(boxBodyFn().text().trim().length, 0);
  });
  TTT.action(function () {
    hideBoxFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(3, function () {
    assert.equal(showBoxFn().length, 1);
    assert.equal(hideBoxFn().length, 0);
    assert.equal(boxBodyFn().text().trim().length, 0);
  });
  TTT.startTest();
});

QUnit.test("Accordion: Box 2: 'hide' to 'show' to 'hide'", function (assert) {
  var boxFn = jQueryFrameFn("#page\\:mainForm\\:accordionBox2");
  var showBoxFn = jQueryFrameFn("#page\\:mainForm\\:showBox2");
  var hideBoxFn = jQueryFrameFn("#page\\:mainForm\\:hideBox2");
  var boxBodyFn = jQueryFrameFn("#page\\:mainForm\\:accordionBox2 .card-body");

  var TTT = new TobagoTestTool(assert);
  TTT.asserts(3, function () {
    assert.equal(showBoxFn().length, 1);
    assert.equal(hideBoxFn().length, 0);
    assert.equal(boxBodyFn().text().trim().length, 0);
  });
  TTT.action(function () {
    showBoxFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(3, function () {
    assert.equal(showBoxFn().length, 0);
    assert.equal(hideBoxFn().length, 1);
    assert.notEqual(boxBodyFn().text().trim().length, 0);
  });
  TTT.action(function () {
    hideBoxFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(3, function () {
    assert.equal(showBoxFn().length, 1);
    assert.equal(hideBoxFn().length, 0);
    assert.equal(boxBodyFn().text().trim().length, 0);
  });
  TTT.startTest();
});

QUnit.test("Accordion: Box 3: 'hide' to 'show' to 'hide'", function (assert) {
  var boxFn = jQueryFrameFn("#page\\:mainForm\\:accordionBox3");
  var showBoxFn = jQueryFrameFn("#page\\:mainForm\\:showBox3");
  var hideBoxFn = jQueryFrameFn("#page\\:mainForm\\:hideBox3");

  var TTT = new TobagoTestTool(assert);
  TTT.asserts(3, function () {
    assert.ok(boxFn().hasClass("tobago-collapsed"));
    assert.equal(showBoxFn().length, 1);
    assert.equal(hideBoxFn().length, 0);
  });
  TTT.action(function () {
    showBoxFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(3, function () {
    assert.notOk(boxFn().hasClass("tobago-collapsed"));
    assert.equal(showBoxFn().length, 0);
    assert.equal(hideBoxFn().length, 1);
  });
  TTT.action(function () {
    hideBoxFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(3, function () {
    assert.ok(boxFn().hasClass("tobago-collapsed"));
    assert.equal(showBoxFn().length, 1);
    assert.equal(hideBoxFn().length, 0);
  });
  TTT.startTest();
});
