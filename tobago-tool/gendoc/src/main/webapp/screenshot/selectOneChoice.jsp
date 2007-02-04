<%--
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
--%>
<%@ taglib uri="http://myfaces.apache.org/tobago/component" prefix="tc" %>
<%@ taglib uri="http://myfaces.apache.org/tobago/extension" prefix="tx" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>

<layout:screenshot>
  <f:subview id="selectOneChoice">
    <jsp:body>
      <tc:panel>
        <f:facet name="layout">
          <tc:gridLayout rows="fixed;fixed;fixed;1*;1*" />
        </f:facet>
<%-- code-sniplet-start id="selectOneChoice" --%>
        <tx:selectOneChoice inline="true" value="selectOneChoice0"
                           label="Contact me1: " >
          <tc:selectItem itemLabel="Letter" itemValue="letter" />
          <tc:selectItem itemLabel="Phone" itemValue="phone" />
          <tc:selectItem itemLabel="eMail" itemValue="eMail" />
          <tc:selectItem itemLabel="Fax" itemValue="fax" />
        </tx:selectOneChoice>

<%-- code-sniplet-end id="selectOneChoice" --%>

        <%-- surround this with a tc:form if you get validatation or required messages from other fields.
          With tc:form you enable partial validation and update model.
           <tc:form>
        --%>
        <tx:selectOneChoice value="#{reference.vehicle}"
                           label="Vehicle: " valueChangeListener="#{reference.valueChanged}" >
          <f:selectItems value="#{reference.selectItems}" />
          <f:facet name="change">
            <tc:command action="#{reference.action}"/>
          </f:facet>
        </tx:selectOneChoice>
        <tx:selectOneChoice value="#{reference.manufacturer}"
                           label="Manufacturer: " valueChangeListener="#{reference.valueChanged}" >
          <f:selectItems value="#{reference.manufacturerSelectItems}" />
        </tx:selectOneChoice>
        <%-- </tc:form> --%>
        <tc:messages></tc:messages>
        <tc:cell/>

      </tc:panel>

    </jsp:body>
  </f:subview>
</layout:screenshot>