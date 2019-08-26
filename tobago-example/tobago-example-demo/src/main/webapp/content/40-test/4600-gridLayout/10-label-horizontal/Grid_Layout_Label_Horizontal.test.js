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

import {testFrameQuerySelectorFn} from "/script/tobago-test.js";
import {TobagoTestTool} from "/tobago/test/tobago-test-tool.js";

QUnit.test("test CSS of the fields and labels of 'first1'", function (assert) {
  assert.expect(8);

  let fieldFn = testFrameQuerySelectorFn("#page\\:mainForm\\:first1");
  let labelFn = testFrameQuerySelectorFn("#page\\:mainForm\\:first1\\:\\:label");

  TobagoTestTool.checkGridCss(assert, fieldFn(), "2", "auto", "3", "auto");
  TobagoTestTool.checkGridCss(assert, labelFn(), "1", "auto", "3", "auto");
});

QUnit.test("test CSS of the fields and labels of 'last1'", function (assert) {
  assert.expect(8);

  let fieldFn = testFrameQuerySelectorFn("#page\\:mainForm\\:last1");
  let labelFn = testFrameQuerySelectorFn("#page\\:mainForm\\:last1\\:\\:label");

  TobagoTestTool.checkGridCss(assert, fieldFn(), "3", "auto", "3", "auto");
  TobagoTestTool.checkGridCss(assert, labelFn(), "4", "auto", "3", "auto");
});

QUnit.test("test CSS of the fields and labels of 'first2'", function (assert) {
  assert.expect(8);

  let fieldFn = testFrameQuerySelectorFn("#page\\:mainForm\\:first2");
  let labelFn = testFrameQuerySelectorFn("#page\\:mainForm\\:first2\\:\\:label");

  TobagoTestTool.checkGridCss(assert, fieldFn(), "1", "auto", "5", "auto");
  TobagoTestTool.checkGridCss(assert, labelFn(), "2", "auto", "5", "auto");
});

QUnit.test("test CSS of the fields and labels of 'last2'", function (assert) {
  assert.expect(8);

  let fieldFn = testFrameQuerySelectorFn("#page\\:mainForm\\:last2");
  let labelFn = testFrameQuerySelectorFn("#page\\:mainForm\\:last2\\:\\:label");

  TobagoTestTool.checkGridCss(assert, fieldFn(), "4", "auto", "5", "auto");
  TobagoTestTool.checkGridCss(assert, labelFn(), "3", "auto", "5", "auto");
});

QUnit.test("test CSS of the fields and labels of 'first3'", function (assert) {
  assert.expect(8);

  let fieldFn = testFrameQuerySelectorFn("#page\\:mainForm\\:first3");
  let labelFn = testFrameQuerySelectorFn("#page\\:mainForm\\:first3\\:\\:label");

  TobagoTestTool.checkGridCss(assert, fieldFn(), "2", "span 3", "7", "auto");
  TobagoTestTool.checkGridCss(assert, labelFn(), "1", "auto", "7", "auto");
});

QUnit.test("test CSS of the fields and labels of 'last3'", function (assert) {
  assert.expect(8);

  let fieldFn = testFrameQuerySelectorFn("#page\\:mainForm\\:last3");
  let labelFn = testFrameQuerySelectorFn("#page\\:mainForm\\:last3\\:\\:label");

  TobagoTestTool.checkGridCss(assert, fieldFn(), "1", "span 3", "8", "auto");
  TobagoTestTool.checkGridCss(assert, labelFn(), "4", "auto", "8", "auto");
});

QUnit.test("test CSS of the fields and labels of 'first4'", function (assert) {
  assert.expect(8);

  let fieldFn = testFrameQuerySelectorFn("#page\\:mainForm\\:first4");
  let labelFn = testFrameQuerySelectorFn("#page\\:mainForm\\:first4\\:\\:label");

  TobagoTestTool.checkGridCss(assert, fieldFn(), "2", "span 2", "10", "auto");
  TobagoTestTool.checkGridCss(assert, labelFn(), "1", "auto", "10", "auto");
});

QUnit.test("test CSS of the fields and labels of 'last4'", function (assert) {
  assert.expect(8);

  let fieldFn = testFrameQuerySelectorFn("#page\\:mainForm\\:last4");
  let labelFn = testFrameQuerySelectorFn("#page\\:mainForm\\:last4\\:\\:label");

  TobagoTestTool.checkGridCss(assert, fieldFn(), "1", "span 2", "11", "auto");
  TobagoTestTool.checkGridCss(assert, labelFn(), "3", "auto", "11", "auto");
});

QUnit.test("test CSS of the fields and labels of 'first5'", function (assert) {
  assert.expect(8);

  let fieldFn = testFrameQuerySelectorFn("#page\\:mainForm\\:first5");
  let labelFn = testFrameQuerySelectorFn("#page\\:mainForm\\:first5\\:\\:label");

  TobagoTestTool.checkGridCss(assert, fieldFn(), "3", "span 2", "13", "auto");
  TobagoTestTool.checkGridCss(assert, labelFn(), "2", "auto", "13", "auto");
});

QUnit.test("test CSS of the fields and labels of 'last5'", function (assert) {
  assert.expect(8);

  let fieldFn = testFrameQuerySelectorFn("#page\\:mainForm\\:last5");
  let labelFn = testFrameQuerySelectorFn("#page\\:mainForm\\:last5\\:\\:label");

  TobagoTestTool.checkGridCss(assert, fieldFn(), "2", "span 2", "14", "auto");
  TobagoTestTool.checkGridCss(assert, labelFn(), "4", "auto", "14", "auto");
});

QUnit.test("test CSS of the fields and labels of 'first6'", function (assert) {
  assert.expect(8);

  let fieldFn = testFrameQuerySelectorFn("#page\\:mainForm\\:first6");
  let labelFn = testFrameQuerySelectorFn("#page\\:mainForm\\:first6\\:\\:label");

  TobagoTestTool.checkGridCss(assert, fieldFn(), "2", "span 4", "16", "auto");
  TobagoTestTool.checkGridCss(assert, labelFn(), "1", "auto", "16", "auto");
});

QUnit.test("test CSS of the fields and labels of 'last6'", function (assert) {
  assert.expect(8);

  let fieldFn = testFrameQuerySelectorFn("#page\\:mainForm\\:last6");
  let labelFn = testFrameQuerySelectorFn("#page\\:mainForm\\:last6\\:\\:label");

  TobagoTestTool.checkGridCss(assert, fieldFn(), "1", "span 4", "17", "auto");
  TobagoTestTool.checkGridCss(assert, labelFn(), "5", "auto", "17", "auto");
});
