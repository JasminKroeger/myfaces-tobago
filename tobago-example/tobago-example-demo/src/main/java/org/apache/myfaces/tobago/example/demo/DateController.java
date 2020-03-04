/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.myfaces.tobago.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequestScoped
@Named
public class DateController implements Serializable {

  private static final Logger LOG = LoggerFactory.getLogger(DateController.class);

  private Date once;
  private Date onchange;
  private Date submitDate;

  public DateController() {
    once = new Date();
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      submitDate = sdf.parse("2016-05-22");
    } catch (final ParseException e) {
      LOG.error("", e);
    }
  }

  public Date getOnce() {
    return once;
  }

  public void setOnce(final Date once) {
    this.once = once;
  }

  public Date getOnchange() {
    return onchange;
  }

  public void setOnchange(final Date onchange) {
    this.onchange = onchange;
  }

  public Date getNow() {
    return new Date();
  }

  public long getTimestamp() {
    return new Date().getTime();
  }

  public Date getSubmitDate() {
    return submitDate;
  }

  public void setSubmitDate(final Date submitDate) {
    this.submitDate = submitDate;
  }
}
