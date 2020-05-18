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

class SelectOneRadio extends HTMLElement {

  private oldCheckedId: string = "";

  constructor() {
    super();
  }

  connectedCallback(): void {
    this.saveSelection();
    for (const radio of this.radioGroup) {
      radio.addEventListener("click", this.clickSelection.bind(this));
      radio.addEventListener("keydown", this.keySelection.bind(this));
    }
  }

  private clickSelection(event: MouseEvent): void {
    const radio = event.currentTarget as HTMLInputElement;

    if (radio.readOnly) {
      this.revertSelection();
    } else if (!radio.disabled && !radio.required && radio.id === this.oldCheckedId) {
      radio.checked = false;
      this.oldCheckedId = "";
    }

    this.saveSelection();
  }

  private keySelection(event: KeyboardEvent): void {
    const radio = event.currentTarget as HTMLInputElement;
    if (event.code === "Space") {
      if (radio.readOnly) {
        this.revertSelection();
      } else if (!radio.disabled && !radio.required && radio.id === this.oldCheckedId) {
        radio.checked = false;
        this.oldCheckedId = "";
      }
      this.saveSelection();
    }
  }

  private revertSelection(): void {
    for (const radio of this.radioGroup) {
      radio.checked = radio.id === this.oldCheckedId;
    }
  }

  private saveSelection(): void {
    for (const radio of this.radioGroup) {
      if (radio.checked) {
        this.oldCheckedId = radio.id;
      }
    }
  }

  get radioGroup(): NodeListOf<HTMLInputElement> {
    return this.querySelectorAll<HTMLInputElement>("input[type='radio'][name='" + this.id + "']");
  }
}

document.addEventListener("DOMContentLoaded", function (event: Event): void {
  window.customElements.define("tobago-select-one-radio", SelectOneRadio);
});
