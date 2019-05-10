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

interface HTMLElement {
  tobagoSelfOrElementsByClassName(className: string): Array<HTMLElement>;

  tobagoSelfOrQuerySelectorAll(selectors: string): Array<HTMLElement>;

  tobagoPreviousElementSibling(): HTMLElement;

  tobagoNextElementSibling(): HTMLElement;
}

interface Document {
  tobagoPage(): HTMLElement;
}

/**
 * Find all elements (and also self) which have the class "className".
 * @param className Class of elements to find.
 */
HTMLElement.prototype.tobagoSelfOrElementsByClassName = function (className: string): Array<HTMLElement> {
  const result: Array<HTMLElement> = new Array<HTMLElement>();
  if (this.classList.contains(className)) {
    result.push(this);
  }
  for (const found of this.getElementsByClassName(className)) {
    result.push(found);
  }
  return result;
};

/**
 * Find all elements (and also self) which have the attribute "attributeName".
 * @param selectors Name of the attribute of the elements to find.
 */
// todo: may return NodeListOf<HTMLElementTagNameMap[K]> or something like that.
HTMLElement.prototype.tobagoSelfOrQuerySelectorAll = function (selectors: string): Array<HTMLElement> {
  const result: Array<HTMLElement> = new Array<HTMLElement>();
  if (this.matches(selectors)) {
    result.push(this);
  }
  for (const found of this.querySelectorAll(selectors)) {
    result.push(found);
  }
  return result;
};

/**
 * Get the previous sibling element (without <style> elements).
 */
HTMLElement.prototype.tobagoPreviousElementSibling = function (): HTMLElement {
  let sibling: HTMLElement = this.previousElementSibling;
  while (sibling != null) {
    if (sibling.tagName !== "STYLE") {
      return sibling;
    }
    sibling = <HTMLElement>sibling.previousElementSibling;
  }
  return null;
};

/**
 * Get the next sibling element (without <style> elements).
 */
HTMLElement.prototype.tobagoNextElementSibling = function (): HTMLElement {
  let sibling: HTMLElement = this.nextElementSibling;
  while (sibling !== null) {
    if (sibling.tagName !== "STYLE") {
      return sibling;
    }
    sibling = <HTMLElement>this.nextElementSibling;
  }
  return null;
};

Document.prototype.tobagoPage = function (): HTMLElement {
  const pages = this.getElementsByClassName("tobago-page");
  if (pages.length > 0) {
    if (pages.length >= 2) {
      console.warn("Found more than one tobago page!");
    }
    return pages.item(0);
  }
  return null;
};

namespace Tobago {

  export function querySelectorAllOrSelfByClass(
      element: HTMLElement, classNameSelector: string): Array<HTMLElement> {

    const result: Array<HTMLElement> = new Array<HTMLElement>();
    if (element.classList.contains(classNameSelector)) {
      result.push(element);
    }
    for (const found of element.getElementsByClassName(classNameSelector)) {
      result.push(<HTMLElement>found);
    }
    return result;
  }

  /**
   * @deprecated not implemented
   * @param element
   * @param selectors
   */
  export function is(element: HTMLElement, selectors: string) {
    console.error("todo");
  }
}

Tobago4.Utils = {};

/**
 *
 * @param id A JSF client id, type=string. Example: escapeClientId("page:input") -> "#page\\:input"
 * @return A string which can be used as a jQuery selector.
 */
Tobago4.Utils.escapeClientId = function (id) {
  return '#' + id.replace(/([:\.])/g, '\\$1');
};

/**
 * Helps to select either elements from the whole DOM or only find in sub trees
 * (in the case of AJAX partial rendering)
 * @param elements a jQuery object to initialize (ajax) or null for initializing the whole document (full load).
 * @param selector a jQuery selector.
 */
Tobago4.Utils.selectWithJQuery = function (elements, selector) {
  elements = elements.jQuery ? elements : jQuery(elements); // fixme jQuery -> ES5
  return elements == null
      ? jQuery(selector)
      : elements.find(selector).add(elements.filter(selector));
};

Tobago4.Utils.findSubComponent = function (element, subId) {
  return jQuery(Tobago4.Utils.getSubComponentId(element.attr('id'), subId));
};

Tobago4.Utils.getSubComponentId = function (id, subId) {
  if (id != null) {
    return "#" + id.replace(/:/g, "\\:") + "\\:\\:" + subId;
  } else {
    return null;
  }
};

/** @deprecated */
Tobago4.Utils.findSuperComponent = function (element) {
  return jQuery(Tobago4.Utils.getSuperComponentId(element.attr('id')));
};

Tobago4.Utils.getSuperComponentId = function (id) {
  return "#" + id.substring(0, id.lastIndexOf("::")).replace(/:/g, "\\:");
};

/**
 * "a:b" -> "a"
 * "a:b:c" -> "a:b"
 * "a" -> null
 * null -> null
 * "a:b::sub-component" -> "a"
 * "a::sub-component:b" -> "a::sub-component" // should currently not happen in Tobago
 *
 * @param id The clientId of a component.
 * @return The clientId of the naming container.
 */
Tobago4.Utils.getNamingContainerId = function (id) {
  if (id == null) {
    return null;
  }
  if (id.lastIndexOf(":") == -1) {
    return null;
  }
  while (true) {
    var sub = id.lastIndexOf("::");
    if (sub == -1) {
      break;
    }
    if (sub + 1 == id.lastIndexOf(":")) {
      id = id.substring(0, sub);
    } else {
      break;
    }
  }
  return id.substring(0, id.lastIndexOf(":"));
};

/**
 * fix position, when the element it is outside of the current page
 * @param elements is an jQuery Array of elements to be fixed.
 */
Tobago4.Utils.keepElementInVisibleArea = function (elements) {
  elements.each(function () {
    var element = jQuery(this);
    var page = jQuery(".tobago-page-content:first");
    var left = element.offset().left;
    var top = element.offset().top;
    // fix menu position, when it is outside of the current page
    left = Math.max(0, Math.min(left, page.outerWidth() - element.outerWidth()));
    top = Math.max(0, Math.min(top, page.outerHeight() - element.outerHeight()));
    element.css('left', left);
    element.css('top', top);
  });
};

Tobago4.Utils.addDataMarkup = function (element, markupString) {
  var dataTobagoMarkup = element.attr("data-tobago-markup");
  if (dataTobagoMarkup !== undefined) {
    var markups = jQuery.parseJSON(dataTobagoMarkup);
    markups.push(markupString);
    element.attr("data-tobago-markup", JSON.stringify(markups));
  } else {
    element.attr("data-tobago-markup", JSON.stringify(markupString));
  }
};

Tobago4.Utils.removeDataMarkup = function (element, markupString) {
  var dataTobagoMarkup = element.attr("data-tobago-markup");
  if (dataTobagoMarkup !== undefined) {
    var markups = jQuery.parseJSON(dataTobagoMarkup);
    var index = jQuery.inArray(markupString, markups);
    if (index >= 0) {
      markups.splice(index, 1);
    } else if (markups === markupString) {
      markups = [];
    }

    if (markups.length > 0) {
      element.attr("data-tobago-markup", JSON.stringify(markups));
    } else {
      element.removeAttr("data-tobago-markup");
    }
  }
};
