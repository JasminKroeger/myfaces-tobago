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

QUnit.test("inputfield with label", function (assert) {
  let $dateField = jQueryFrame("#page\\:mainForm\\:dateNoPattern\\:\\:field");
  let $dateButton = jQueryFrame("#page\\:mainForm\\:dateNoPattern button");

  assert.equal($dateField.val(), "");

  $datepicker = jQueryFrame(".bootstrap-datetimepicker-widget");
  assert.notOk($datepicker.get(0));

  $dateButton.click();

  $datepicker = jQueryFrame(".bootstrap-datetimepicker-widget");
  assert.ok($datepicker.get(0));

  assert.notEqual($dateField.val(), "");
});
