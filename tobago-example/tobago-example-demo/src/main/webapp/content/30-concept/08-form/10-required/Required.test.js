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

QUnit.test("submit inner form 1 without violations", function (assert) {
  var form1InputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm1\\:in1\\:\\:field");
  var form1SubmitButtonFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm1\\:submit1");
  var form1OutputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm1\\:out1 span");
  var alertFn = jQueryFrameFn("#page\\:messages .alert-danger label");

  var TTT = new TobagoTestTool(assert);
  TTT.action(function () {
    form1InputFieldFn().val("Alice");
    form1SubmitButtonFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(3, function () {
    assert.equal(form1InputFieldFn().val(), "Alice");
    assert.equal(form1OutputFieldFn().text(), "Alice");
    assert.equal(alertFn().length, "0");
  });
  TTT.startTest();
});

QUnit.test("submit inner form 2, violate required field", function (assert) {
  var form2InputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm2\\:in2\\:\\:field");
  var form2SubmitButtonFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm2\\:submit2");
  var form2OutputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm2\\:out2 span");
  var form2OutputFieldValue = form2OutputFieldFn().text();
  var alertFn = jQueryFrameFn("#page\\:messages .alert-danger label");

  var TTT = new TobagoTestTool(assert);
  TTT.action(function () {
    form2InputFieldFn().val("");
  });
  TTT.asserts(1, function () {
    assert.equal(form2InputFieldFn().val(), "");
  });
  TTT.action(function () {
    form2SubmitButtonFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(3, function () {
    assert.equal(form2InputFieldFn().val(), "");
    assert.equal(form2OutputFieldFn().text(), form2OutputFieldValue);
    assert.equal(alertFn().length, "1");
  });
  TTT.startTest();
});

QUnit.test("submit inner form 2 without violations", function (assert) {
  var form2InputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm2\\:in2\\:\\:field");
  var form2SubmitButtonFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm2\\:submit2");
  var form2OutputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm2\\:out2 span");
  var alertFn = jQueryFrameFn("#page\\:messages .alert-danger label");

  var TTT = new TobagoTestTool(assert);
  TTT.action(function () {
    form2InputFieldFn().val("Bob");
  });
  TTT.asserts(1, function () {
    assert.equal(form2InputFieldFn().val(), "Bob");
  });
  TTT.action(function () {
    form2SubmitButtonFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(3, function () {
    assert.equal(form2InputFieldFn().val(), "Bob");
    assert.equal(form2OutputFieldFn().text(), "Bob");
    assert.equal(alertFn().length, "0");
  });
  TTT.startTest();
});

QUnit.test("submit outer form, violate both required fields", function (assert) {
  var form2InputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm2\\:in2\\:\\:field");
  var outerFormInputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:in\\:\\:field");
  var outerFormSubmitButtonFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:submit");
  var form2OutputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm2\\:out2 span");
  var outerFormOutputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:out span");
  var alertFn = jQueryFrameFn("#page\\:messages .alert-danger label");

  var form2OutputFieldValue = form2OutputFieldFn().text();
  var outerFormOutputFieldValue = outerFormOutputFieldFn().text();

  var TTT = new TobagoTestTool(assert);
  TTT.action(function () {
    form2InputFieldFn().val("");
    outerFormInputFieldFn().val("");
    outerFormSubmitButtonFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(5, function () {
    assert.equal(form2InputFieldFn().val(), "");
    assert.equal(form2OutputFieldFn().text(), form2OutputFieldValue);
    assert.equal(outerFormInputFieldFn().val(), "");
    assert.equal(outerFormOutputFieldFn().text(), outerFormOutputFieldValue);
    assert.equal(alertFn().length, "2");
  });
  TTT.startTest();
});

QUnit.test("submit outer form, violate required field in form 2", function (assert) {
  var form2InputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm2\\:in2\\:\\:field");
  var outerFormInputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:in\\:\\:field");
  var outerFormSubmitButtonFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:submit");
  var form2OutputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm2\\:out2 span");
  var outerFormOutputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:out span");
  var alertFn = jQueryFrameFn("#page\\:messages .alert-danger label");

  var form2OutputFieldValue = form2OutputFieldFn().text();
  var outerFormOutputFieldValue = outerFormOutputFieldFn().text();

  var TTT = new TobagoTestTool(assert);
  TTT.action(function () {
    form2InputFieldFn().val("");
    outerFormInputFieldFn().val("Charlie");
    outerFormSubmitButtonFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(5, function () {
    assert.equal(form2InputFieldFn().val(), "");
    assert.equal(form2OutputFieldFn().text(), form2OutputFieldValue);
    assert.equal(outerFormInputFieldFn().val(), "Charlie");
    assert.equal(outerFormOutputFieldFn().text(), outerFormOutputFieldValue);
    assert.equal(alertFn().length, "1");
  });
  TTT.startTest();
});

QUnit.test("submit outer form, violate required field in outer form", function (assert) {
  var form2InputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm2\\:in2\\:\\:field");
  var outerFormInputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:in\\:\\:field");
  var outerFormSubmitButtonFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:submit");
  var form2OutputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm2\\:out2 span");
  var outerFormOutputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:out span");
  var alertFn = jQueryFrameFn("#page\\:messages .alert-danger label");

  var form2OutputFieldValue = form2OutputFieldFn().text();
  var outerFormOutputFieldValue = outerFormOutputFieldFn().text();

  var TTT = new TobagoTestTool(assert);
  TTT.action(function () {
    form2InputFieldFn().val("Dave");
    outerFormInputFieldFn().val("");
    outerFormSubmitButtonFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(5, function () {
    assert.equal(form2InputFieldFn().val(), "Dave");
    assert.equal(form2OutputFieldFn().text(), form2OutputFieldValue);
    assert.equal(outerFormInputFieldFn().val(), "");
    assert.equal(outerFormOutputFieldFn().text(), outerFormOutputFieldValue);
    assert.equal(alertFn().length, "1");
  });
  TTT.startTest();
});

QUnit.test("submit outer form without violations", function (assert) {
  var form1InputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm1\\:in1\\:\\:field");
  var form2InputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm2\\:in2\\:\\:field");
  var outerFormInputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:in\\:\\:field");
  var outerFormSubmitButtonFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:submit");
  var form1OutputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm1\\:out1 span");
  var form2OutputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:innerForm2\\:out2 span");
  var outerFormOutputFieldFn = jQueryFrameFn("#page\\:mainForm\\:outerForm\\:out span");
  var alertFn = jQueryFrameFn("#page\\:messages .alert-danger label");

  var TTT = new TobagoTestTool(assert);
  TTT.action(function () {
    form1InputFieldFn().val("Eve");
    form2InputFieldFn().val("Frank");
    outerFormInputFieldFn().val("Grace");
    outerFormSubmitButtonFn().click();
  });
  TTT.waitForResponse();
  TTT.asserts(7, function () {
    assert.equal(form1InputFieldFn().val(), "Eve");
    assert.equal(form1OutputFieldFn().text(), "Eve");
    assert.equal(form2InputFieldFn().val(), "Frank");
    assert.equal(form2OutputFieldFn().text(), "Frank");
    assert.equal(outerFormInputFieldFn().val(), "Grace");
    assert.equal(outerFormOutputFieldFn().text(), "Grace");
    assert.equal(alertFn().length, "0");
  });
  TTT.startTest();
});
