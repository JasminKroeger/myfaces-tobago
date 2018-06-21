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

QUnit.test("Simple Collapsible Box: show -> hide transition", function (assert) {
  assert.expect(2);
  var done = assert.async(2);
  var step = 1;

  var s = "#page\\:mainForm\\:controller\\:show";
  var $show = jQueryFrame(s);
  var h = "#page\\:mainForm\\:controller\\:hide";
  var $hide = jQueryFrame(h);
  var c = "#page\\:mainForm\\:controller\\:content";
  var $content = jQueryFrame(c);

  $show.click();

  jQuery("#page\\:testframe").on("load", function () {
    if (step === 1) {
      $hide = jQueryFrame(h);
      $content = jQueryFrame(c);

      assert.equal($content.length, 1);
      $hide.click();

      step++;
      done();
    } else if (step === 2) {
      $content = jQueryFrame(c);

      assert.equal($content.length, 0);

      step++;
      done();
    }
  });
});

QUnit.test("Simple Collapsible Box: hide -> show transition", function (assert) {
  assert.expect(2);
  var done = assert.async(2);
  var step = 1;

  var s = "#page\\:mainForm\\:controller\\:show";
  var $show = jQueryFrame(s)  ;
  var h = "#page\\:mainForm\\:controller\\:hide";
  var $hide = jQueryFrame(h);
  var c = "#page\\:mainForm\\:controller\\:content";
  var $content = jQueryFrame(c);

  $hide.click();

  jQuery("#page\\:testframe").on("load", function () {
    if (step === 1) {
      $show = jQueryFrame(s);
      $content = jQueryFrame(c);

      assert.equal($content.length, 0);
      $show.click();

      step++;
      done();
    } else if (step === 2) {
      $content = jQueryFrame(c);

      assert.equal($content.length, 1);

      step++;
      done();
    }
  });
});

QUnit.test("Full Server Request: open both boxes", function (assert) {
  assert.expect(4);
  var done = assert.async(2);
  var step = 1;

  var s1 = "#page\\:mainForm\\:server\\:show1";
  var $show1 = jQueryFrame(s1);
  var s2 = "#page\\:mainForm\\:server\\:show2";
  var $show2 = jQueryFrame(s2);
  var c1 = "#page\\:mainForm\\:server\\:content1";
  var $content1 = jQueryFrame(c1);
  var c2 = "#page\\:mainForm\\:server\\:content2";
  var $content2 = jQueryFrame(c2);
  var content2Length = $content2.length;

  $show1.click();

  jQuery("#page\\:testframe").on("load", function () {
    if (step === 1) {
      $show2 = jQueryFrame(s2);
      $content1 = jQueryFrame(c1);
      $content2 = jQueryFrame(c2);

      assert.equal($content1.length, 1);
      assert.equal($content2.length, content2Length);
      $show2.click();

      step++;
      done();
    } else if (step === 2) {
      $content1 = jQueryFrame(c1);
      $content2 = jQueryFrame(c2);

      assert.equal($content1.length, 1);
      assert.equal($content2.length, 1);

      step++;
      done();
    }
  });
});

QUnit.test("Full Server Request: open box 1, close box 2", function (assert) {
  assert.expect(4);
  var done = assert.async(2);
  var step = 1;

  var s1 = "#page\\:mainForm\\:server\\:show1";
  var $show1 = jQueryFrame(s1);
  var h2 = "#page\\:mainForm\\:server\\:hide2";
  var $hide2 = jQueryFrame(h2);
  var c1 = "#page\\:mainForm\\:server\\:content1";
  var $content1 = jQueryFrame(c1);
  var c2 = "#page\\:mainForm\\:server\\:content2";
  var $content2 = jQueryFrame(c2);
  var content2Length = $content2.length;

  $show1.click();

  jQuery("#page\\:testframe").on("load", function () {
    if (step === 1) {
      $hide2 = jQueryFrame(h2);
      $content1 = jQueryFrame(c1);
      $content2 = jQueryFrame(c2);

      assert.equal($content1.length, 1);
      assert.equal($content2.length, content2Length);
      $hide2.click();

      step++;
      done();
    } else if (step === 2) {
      $content1 = jQueryFrame(c1);
      $content2 = jQueryFrame(c2);

      assert.equal($content1.length, 1);
      assert.equal($content2.length, 0);

      step++;
      done();
    }
  });
});

QUnit.test("Full Server Request: close box 1, open box 2", function (assert) {
  assert.expect(4);
  var done = assert.async(2);
  var step = 1;

  var h1 = "#page\\:mainForm\\:server\\:hide1";
  var $hide1 = jQueryFrame(h1);
  var s2 = "#page\\:mainForm\\:server\\:show2";
  var $show2 = jQueryFrame(s2);
  var c1 = "#page\\:mainForm\\:server\\:content1";
  var $content1 = jQueryFrame(c1);
  var c2 = "#page\\:mainForm\\:server\\:content2";
  var $content2 = jQueryFrame(c2);
  var content2Length = $content2.length;

  $hide1.click();

  jQuery("#page\\:testframe").on("load", function () {
    if (step === 1) {
      $show2 = jQueryFrame(s2);
      $content1 = jQueryFrame(c1);
      $content2 = jQueryFrame(c2);

      assert.equal($content1.length, 0);
      assert.equal($content2.length, content2Length);
      $show2.click();

      step++;
      done();
    } else if (step === 2) {
      $content1 = jQueryFrame(c1);
      $content2 = jQueryFrame(c2);

      assert.equal($content1.length, 0);
      assert.equal($content2.length, 1);

      step++;
      done();
    }
  });
});

QUnit.test("Full Server Request: close both boxes", function (assert) {
  assert.expect(4);
  var done = assert.async(2);
  var step = 1;

  var h1 = "#page\\:mainForm\\:server\\:hide1";
  var $hide1 = jQueryFrame(h1);
  var h2 = "#page\\:mainForm\\:server\\:hide2";
  var $hide2 = jQueryFrame(h2);
  var c1 = "#page\\:mainForm\\:server\\:content1";
  var $content1 = jQueryFrame(c1);
  var c2 = "#page\\:mainForm\\:server\\:content2";
  var $content2 = jQueryFrame(c2);
  var content2Length = $content2.length;

  $hide1.click();

  jQuery("#page\\:testframe").on("load", function () {
    if (step === 1) {
      $hide2 = jQueryFrame(h2);
      $content1 = jQueryFrame(c1);
      $content2 = jQueryFrame(c2);

      assert.equal($content1.length, 0);
      assert.equal($content2.length, content2Length);
      $hide2.click();

      step++;
      done();
    } else if (step === 2) {
      $content1 = jQueryFrame(c1);
      $content2 = jQueryFrame(c2);

      assert.equal($content1.length, 0);
      assert.equal($content2.length, 0);

      step++;
      done();
    }
  });
});

QUnit.test("Client Sided: show -> hide transition", function (assert) {
  assert.expect(2);

  var $show = jQueryFrame("#page\\:mainForm\\:client\\:showNoRequestBox");
  var $hide = jQueryFrame("#page\\:mainForm\\:client\\:hideNoRequestBox");
  var $box = jQueryFrame("#page\\:mainForm\\:client\\:noRequestBox");

  $show.click();
  assert.equal($box.hasClass("tobago-collapsed"), false);

  $hide.click();
  assert.equal($box.hasClass("tobago-collapsed"), true);
});

QUnit.test("Client Sided: hide -> show transition", function (assert) {
  assert.expect(2);

  var $show = jQueryFrame("#page\\:mainForm\\:client\\:showNoRequestBox");
  var $hide = jQueryFrame("#page\\:mainForm\\:client\\:hideNoRequestBox");
  var $box = jQueryFrame("#page\\:mainForm\\:client\\:noRequestBox");

  $hide.click();
  assert.equal($box.hasClass("tobago-collapsed"), true);

  $show.click();
  assert.equal($box.hasClass("tobago-collapsed"), false);
});

QUnit.test("Client Sided: hide content and submit empty string", function (assert) {
  assert.expect(2);
  var done = assert.async();

  var m = "#page\\:messages .tobago-messages";
  var $messages = jQueryFrame(m);
  var $show = jQueryFrame("#page\\:mainForm\\:client\\:showNoRequestBox");
  var $hide = jQueryFrame("#page\\:mainForm\\:client\\:hideNoRequestBox");
  var $box = jQueryFrame("#page\\:mainForm\\:client\\:noRequestBox");
  var $in = jQueryFrame("#page\\:mainForm\\:client\\:inNoRequestBox\\:\\:field");
  var $submit = jQueryFrame("#page\\:mainForm\\:client\\:submitNoRequestBox");

  $hide.click();
  assert.equal($box.hasClass("tobago-collapsed"), true);

  $in.val("");
  $submit.click();

  jQuery("#page\\:testframe").on("load", function () {
    $messages = jQueryFrame(m);
    assert.equal($messages.length, 1);
    done();
  });
});

QUnit.test("Ajax: show -> hide transition", function (assert) {
  assert.expect(2);
  var done = assert.async(2);
  var step = 1;

  var s = "#page\\:mainForm\\:ajax\\:showAjaxBox";
  var $show = jQueryFrame(s);
  var h = "#page\\:mainForm\\:ajax\\:hideAjaxBox";
  var $hide = jQueryFrame(h);
  var i = "#page\\:mainForm\\:ajax\\:inAjaxBox\\:\\:field";
  var $in = jQueryFrame(i);

  $show.click();

  waitForAjax(function () {
    $in = jQueryFrame(i);
    console.log("step: " + step + " active: " + jQuery.active);
    return step === 1 && jQuery.active === 0;
  }, function () {
    $hide = jQueryFrame(h);
    $in = jQueryFrame(i);

    console.log("$hide selector: " + h);
    assert.equal($in.length, 1);
    $hide.click();

    step++;
    done();
  });

  waitForAjax(function () {
    $in = jQueryFrame(i);
    return step === 2 && $in.length === 0;
  }, function () {
    $in = jQueryFrame(i);
    assert.equal($in.length, 0);
    done();
  });
});


QUnit.test("Ajax: hide -> show transition", function (assert) {
  assert.expect(2);
  var done = assert.async(2);
  var step = 1;

  var s = "#page\\:mainForm\\:ajax\\:showAjaxBox";
  var $show = jQueryFrame(s);
  var h = "#page\\:mainForm\\:ajax\\:hideAjaxBox";
  var $hide = jQueryFrame(h);
  var i = "#page\\:mainForm\\:ajax\\:inAjaxBox\\:\\:field";
  var $in = jQueryFrame(i);

  $hide.click();

  waitForAjax(function () {
    $in = jQueryFrame(i);
    return step === 1 && $in.length === 0;
  }, function () {
    $show = jQueryFrame(s);
    $in = jQueryFrame(i);

    assert.equal($in.length, 0);
    $show.click();

    step++;
    done();
  });

  waitForAjax(function () {
    $in = jQueryFrame(i);
    return step === 2 && $in.length === 1;
  }, function () {
    $in = jQueryFrame(i);
    assert.equal($in.length, 1);
    done();
  });
});

QUnit.test("Ajax: hide content and submit empty string", function (assert) {
  assert.expect(3);
  var done = assert.async(3);
  var step = 1;

  var m = "#page\\:messages .tobago-messages";
  var $messages = jQueryFrame(m);
  var $show = jQueryFrame("#page\\:mainForm\\:ajax\\:showAjaxBox");
  var h = "#page\\:mainForm\\:ajax\\:hideAjaxBox";
  var $hide = jQueryFrame(h);
  var i = "#page\\:mainForm\\:ajax\\:inAjaxBox\\:\\:field";
  var $in = jQueryFrame(i);
  var s = "#page\\:mainForm\\:ajax\\:submitAjaxBox";
  var $submit = jQueryFrame(s);

  $show.click();

  waitForAjax(function () {
    $in = jQueryFrame(i);
    return step == 1 && $in.length == 1;
  }, function () {
    $hide = jQueryFrame(h);
    $in = jQueryFrame(i);

    assert.equal($in.length, 1);
    $in.val("");
    $hide.click();

    step++;
    done();
  });

  waitForAjax(function () {
    $in = jQueryFrame(i);
    return step == 2 && $in.length == 0;
  }, function () {
    $in = jQueryFrame(i);
    $submit = jQueryFrame(s);

    assert.equal($in.length, 0);
    $submit.click();

    step++;
    done();
  });

  jQuery("#page\\:testframe").on("load", function () {
    if (step === 3) {
      $messages = jQueryFrame(m);
      assert.equal($messages.length, 0);
      done();
    }
  });
});
