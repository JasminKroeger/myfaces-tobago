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

QUnit.test("Required: Submit without content.", function (assert) {
  assert.expect(1);
  var done = assert.async();

  var m = "#page\\:messages .tobago-messages";
  var $messages = jQueryFrame(m);
  var $in = jQueryFrame("#page\\:mainForm\\:required\\:in\\:\\:field");
  var $submit = jQueryFrame("#page\\:mainForm\\:required\\:submit");

  $in.val("");
  $submit.click();

  jQuery("#page\\:testframe").on("load", function () {
    $messages = jQueryFrame(m);
    assert.equal($messages.length, 1);
    done();
  });
});

QUnit.test("Required: Submit with content.", function (assert) {
  assert.expect(1);
  var done = assert.async();

  var m = "#page\\:messages .tobago-messages";
  var $messages = jQueryFrame(m);
  var $in = jQueryFrame("#page\\:mainForm\\:required\\:in\\:\\:field");
  var $submit = jQueryFrame("#page\\:mainForm\\:required\\:submit");

  $in.val("some content");
  $submit.click();

  jQuery("#page\\:testframe").on("load", function () {
    $messages = jQueryFrame(m);
    assert.equal($messages.length, 0);
    done();
  });
});

QUnit.test("Length: Submit single character.", function (assert) {
  assert.expect(1);
  var done = assert.async();
  var step = 1;

  var m = "#page\\:messages .tobago-messages";
  var $messages = jQueryFrame(m);
  var $in = jQueryFrame("#page\\:mainForm\\:length\\:in2\\:\\:field");
  var $submit = jQueryFrame("#page\\:mainForm\\:length\\:submit2");

  $in.val("a");
  $submit.click();

  jQuery("#page\\:testframe").on("load", function () {
    if (step === 1) {
      $messages = jQueryFrame(m);
      assert.equal($messages.length, 1);

      step++;
      done();
    }
  });
});

QUnit.test("Length: Submit three characters.", function (assert) {
  assert.expect(1);
  var done = assert.async();
  var step = 1;

  var m = "#page\\:messages .tobago-messages";
  var $messages = jQueryFrame(m);
  var $in = jQueryFrame("#page\\:mainForm\\:length\\:in2\\:\\:field");
  var $submit = jQueryFrame("#page\\:mainForm\\:length\\:submit2");

  $in.val("abc");
  $submit.click();

  jQuery("#page\\:testframe").on("load", function () {
    if (step === 1) {
      $messages = jQueryFrame(m);
      assert.equal($messages.length, 0);

      step++;
      done();
    }
  });
});

QUnit.test("Length: Submit five characters.", function (assert) {
  assert.expect(1);
  var done = assert.async();
  var step = 1;

  var m = "#page\\:messages .tobago-messages";
  var $messages = jQueryFrame(m);
  var $in = jQueryFrame("#page\\:mainForm\\:length\\:in2\\:\\:field");
  var $submit = jQueryFrame("#page\\:mainForm\\:length\\:submit2");

  $in.val("abcde");
  $submit.click();

  jQuery("#page\\:testframe").on("load", function () {
    if (step === 1) {
      $messages = jQueryFrame(m);
      assert.equal($messages.length, 1);

      step++;
      done();
    }
  });
});
