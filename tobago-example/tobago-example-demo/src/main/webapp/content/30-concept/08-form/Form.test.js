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

import {querySelectorFn} from "/script/tobago-test.js";
import {JasmineTestTool} from "/tobago/test/tobago-test-tool.js";

it("submit form 1", function (done) {
  let form1InputFieldFn = querySelectorFn("#page\\:mainForm\\:form1\\:in1\\:\\:field");
  let form2InputFieldFn = querySelectorFn("#page\\:mainForm\\:form2\\:in2\\:\\:field");
  let form1OutputFieldFn = querySelectorFn("#page\\:mainForm\\:form1\\:out1 span");
  let form2OutputFieldFn = querySelectorFn("#page\\:mainForm\\:form2\\:out2 span");
  let form1SubmitButtonFn = querySelectorFn("#page\\:mainForm\\:form1\\:submit1");
  let $form2OutputFieldValue = form2OutputFieldFn().textContent;

  let test = new JasmineTestTool(done);
  test.do(() => form1InputFieldFn().value = "Oliver");
  test.do(() => form2InputFieldFn().value = "Peter");
  test.do(() => form1SubmitButtonFn().dispatchEvent(new Event("click", {bubbles: true})));
  test.wait(() => form1InputFieldFn() && form1InputFieldFn().value === "Oliver");
  test.do(() => expect(form1InputFieldFn().value).toBe("Oliver"));
  test.do(() => expect(form1OutputFieldFn().textContent).toBe("Oliver"));
  test.do(() => expect(form2InputFieldFn().value).toBe("Peter"));
  test.do(() => expect(form2OutputFieldFn().textContent).toBe($form2OutputFieldValue));
  test.start();
});

it("submit form 2", function (done) {
  let form1InputFieldFn = querySelectorFn("#page\\:mainForm\\:form1\\:in1\\:\\:field");
  let form2InputFieldFn = querySelectorFn("#page\\:mainForm\\:form2\\:in2\\:\\:field");
  let form1OutputFieldFn = querySelectorFn("#page\\:mainForm\\:form1\\:out1 span");
  let form2OutputFieldFn = querySelectorFn("#page\\:mainForm\\:form2\\:out2 span");
  let form2SubmitButtonFn = querySelectorFn("#page\\:mainForm\\:form2\\:submit2");
  let $form1OutputFieldValue = form1OutputFieldFn().textContent;

  let test = new JasmineTestTool(done);
  test.do(() => form1InputFieldFn().value = "Oliver");
  test.do(() => form2InputFieldFn().value = "Peter");
  test.do(() => form2SubmitButtonFn().dispatchEvent(new Event("click", {bubbles: true})));
  test.wait(() => form1InputFieldFn() && form1InputFieldFn().value === "Oliver");
  test.do(() => expect(form1InputFieldFn().value).toBe("Oliver"));
  test.do(() => expect(form1OutputFieldFn().textContent).toBe($form1OutputFieldValue));
  test.do(() => expect(form2InputFieldFn().value).toBe("Peter"));
  test.do(() => expect(form2OutputFieldFn().textContent).toBe("Peter"));
  test.start();
});
