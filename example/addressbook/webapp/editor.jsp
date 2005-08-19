<%--
 * Copyright 2002-2005 atanion GmbH.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
--%>
<%@ taglib uri="http://www.atanion.com/tobago/component" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<f:view>
  <t:page label="#{bundle.editorTitle}" width="640px" height="480px">

    <f:loadBundle
        basename="org.apache.myfaces.tobago.example.addressbook.Resources"
        var="bundle" />

    <t:panel>
      <f:facet name="layout">
        <t:gridLayout rows="10px;1*;10px" columns="10px;1*;10px"/>
      </f:facet>

      <t:cell spanX="3" />
      <t:cell/>

      <t:box label="#{bundle.editorBoxTitle}" >
        <f:facet name="layout">
          <t:gridLayout rows="fixed;fixed;fixed;fixed;fixed;fixed;fixed;fixed;fixed;fixed;fixed;1*;fixed"  />
        </f:facet>

        <t:messages />

        <t:in value="#{controller.currentAddress.firstName}"
            label="#{bundle.editorFirstName}" required="true" />

        <t:in value="#{controller.currentAddress.lastName}"
            label="#{bundle.editorLastName}" required="true" rendered="true" />

        <t:panel>
          <f:facet name="layout">
            <t:gridLayout columns="6*;1*" />
          </f:facet>
          <t:in value="#{controller.currentAddress.street}"
              label="#{bundle.editorStreet}" />
          <t:in value="#{controller.currentAddress.houseNumber}" />
        </t:panel>

        <t:panel>
          <f:facet name="layout">
            <t:gridLayout columns="1*;1*" />
          </f:facet>
          <t:in value="#{controller.currentAddress.zipCode}"
              label="#{bundle.editorCity}" />
          <t:in value="#{controller.currentAddress.city}" />
        </t:panel>

        <t:selectOneChoice value="#{controller.currentAddress.country}"
            label="#{bundle.editorCountry}">
          <f:selectItems value="#{countries}" />
        </t:selectOneChoice>

        <t:in value="#{controller.currentAddress.phone}"
            label="#{bundle.editorPhone}" />

        <t:in value="#{controller.currentAddress.mobile}"
            label="#{bundle.editorMobile}" />

        <t:in value="#{controller.currentAddress.fax}"
            label="#{bundle.editorFax}" />

        <t:in value="#{controller.currentAddress.email}"
            label="#{bundle.editorEmail}" />

        <t:date value="#{controller.currentAddress.dayOfBirth}"
            label="#{bundle.editorBirthday}">
          <f:convertDateTime pattern="dd.MM.yyyy" />
        </t:date>

        <t:textarea value="#{controller.currentAddress.note}"
            label="#{bundle.editorNote}" />

        <t:panel>
          <f:facet name="layout">
            <t:gridLayout columns="3*;1*;1*"  />
          </f:facet>

          <t:cell />
          <t:button action="#{controller.storeAddress}"
              labelWithAccessKey="#{bundle.editorStore}" defaultCommand="true" />
          <t:button action="list" immediate="true"
              labelWithAccessKey="#{bundle.editorCancel}" />
        </t:panel>

      </t:box>

      <t:cell/>
      <t:cell spanX="3" />

    </t:panel>
  </t:page>
</f:view>
