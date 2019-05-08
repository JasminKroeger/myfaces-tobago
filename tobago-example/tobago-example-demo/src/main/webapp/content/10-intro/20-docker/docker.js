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

Tobago.Docker = {};

Tobago.Docker.init = function () {

  /* Copy the command lines to the clipboard.
   */
  document.querySelector('#page\\:mainForm\\:toClipboardButton').addEventListener('click', function (event) {
    var commandLine = document.querySelector('#commandLine');

    if (window.getSelection) {
      var selection = window.getSelection();
      var range = document.createRange();
      range.selectNodeContents(commandLine);
      selection.removeAllRanges();
      selection.addRange(range);
    } else {
      console.warn("Text select not possible: Unsupported browser.");
    }

    try {
      document.execCommand('copy');
    } catch (err) {
      console.error('Copying text not possible');
    }
  });
};

Tobago.registerListener(Tobago.Docker.init, Tobago.Phase.DOCUMENT_READY);
Tobago.registerListener(Tobago.Docker.init, Tobago.Phase.AFTER_UPDATE);
