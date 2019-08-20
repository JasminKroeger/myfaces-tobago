package org.apache.myfaces.tobago.internal.taglib.declaration;

import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute;

public interface Select2 {

  /**
   * Flag indicating that this select
   */
  @TagAttribute()
  @UIComponentTagAttribute(type = "boolean", defaultValue = "false", generate = false)
  void setAllowClear(String allowed);

  /**
   * Flag indicating that this select accepts values which are not in option list.
   */
  @TagAttribute()
  @UIComponentTagAttribute(type = "boolean", defaultValue = "false", generate = false)
  void setAllowCustom(String allowed);

//  /**
//   * Flag indicating that this select
//   */
//  @TagAttribute()
//  @UIComponentTagAttribute(type = "string", generate = false)
//  void setMatcher(String allowed);

  /**
   * Flag indicating that this select
   */
  @TagAttribute()
  @UIComponentTagAttribute(type = "int", defaultValue = "0", generate = false)
  void setMaximumInputLength(String allowed);

  /**
   * Flag indicating that this select
   */
  @TagAttribute()
  @UIComponentTagAttribute(type = "int", defaultValue = "0", generate = false)
  void setMinimumInputLength(String allowed);

  /**
   * Flag indicating that this select
   */
  @TagAttribute()
  @UIComponentTagAttribute(type = "int", defaultValue = "0", generate = false)
  void setMaximumSelectionLength(String allowed);

}
