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

(function ($) {
  $.widget("demo.alert", {
    _create: function () {
      this._on({
        click: function (event) {
          var text = this.element.data("alert-text");
          alert(text);
        }
      });
    }
  });
}(jQuery));

var initAlert = function () {
  jQuery("[data-alert-text]").alert();
};

Tobago.registerListener(initAlert, Tobago.Phase.DOCUMENT_READY);
Tobago.registerListener(initAlert, Tobago.Phase.AFTER_UPDATE);

jQuery.fn.select2.amd.define("CustomTokenizerAdapter", [
      "select2/utils",
      "select2/data/tokenizer"
    ],
    function(Utils, Tokenizer) {
      var emptyFunc = function (params) {

      };
      emptyFunc.tokenizer = function (params) {
        var result = Tokenizer.prototype.tokenizer.call(this, params);
        console.info("tokenizer result: " + result);
        return result;
      };
     return function(params) {emptyFunc.tokenizer(params)};
    });

var TBG_DEMO = {
  Select2: {
    Tokenizer: function (params) {
      console.info("params: " + params);
      var tokenizer = jQuery.fn.select2.amd.require("Tokenizer");
      var results = tokenizer.call(this, params);
      console.info("results: " + results);
      return results;
    },

    doOnSelect: function (event) {
      var element = jQuery(this);
      var data = element.select2("data");
      var newData = event.params.data;
      console.info("doOnSelect id     : " +  element.attr("id"));
      console.info("doOnSelect tagName: " +  element.prop("tagName"));
      console.info("doOnSelect newData: " +  newData);
      console.info("doOnSelect data   : " +  data);
      // console.info("doOnSelect : " +  element);
    }
  }
};
