package org.apache.myfaces.tobago.internal.taglib.declaration;

import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute;

public interface Select2One {
  /**
   * Flag indicating that this element is rendered as select2.
   */
  @TagAttribute()
  @UIComponentTagAttribute(type = "boolean", defaultValue = "false")
  void setSelect2(String disabled);

  /**
   * Flag indicating that this element is rendered as select2.
   */
  @TagAttribute()
  @UIComponentTagAttribute(type = "int", defaultValue = "20", generate = false)
  void setMinimumResultsForSearch(String disabled);
}
