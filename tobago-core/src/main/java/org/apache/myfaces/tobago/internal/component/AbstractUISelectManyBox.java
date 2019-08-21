//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.apache.myfaces.tobago.internal.component;

import org.apache.myfaces.tobago.internal.component.AbstractUISelectOneChoice.Select2Keys;

import javax.faces.context.FacesContext;

public abstract class AbstractUISelectManyBox extends AbstractUISelectMany {

  @Override
  protected void validateValue(FacesContext context, Object convertedValue) {
    if (!isAllowCustom()) {
      super.validateValue(context, convertedValue);
    }
  }

  public boolean isAllowClear() {
    Boolean allowClear = (Boolean) getStateHelper().eval(Select2Keys.allowClear);
    if (allowClear != null) {
      return allowClear;
    }
    return false;
  }
  public boolean isAllowClearSet() {
    return getStateHelper().eval(Select2Keys.allowClear) != null;
  }

  public void setAllowClear(boolean allowClear) {
    getStateHelper().put(Select2Keys.allowClear, allowClear);
  }


  public boolean isAllowCustom() {
    Boolean allowCustom = (Boolean) getStateHelper().eval(Select2Keys.allowCustom);
    if (allowCustom != null) {
      return allowCustom;
    }
    return false;
  }
  public boolean isAllowCustomSet() {
    return getStateHelper().eval(Select2Keys.allowCustom) != null;
  }

  public void setAllowCustom(boolean allowCustom) {
    getStateHelper().put(Select2Keys.allowCustom, allowCustom);
  }

  public int getMaximumInputLength() {
    Integer maximumInputLength = (Integer) getStateHelper().eval(Select2Keys.maximumInputLength);
    if (maximumInputLength != null) {
      return maximumInputLength;
    }
    return 0;
  }
  public boolean isMaximumInputLengthSet() {
    return getStateHelper().eval(Select2Keys.maximumInputLength) != null;
  }

  public void setMaximumInputLength(int minimumInputLength) {
    getStateHelper().put(Select2Keys.maximumInputLength, minimumInputLength);
  }

  public int getMinimumInputLength() {
    Integer minimumInputLength = (Integer) getStateHelper().eval(Select2Keys.minimumInputLength);
    if (minimumInputLength != null) {
      return minimumInputLength;
    }
    return 0;
  }
  public boolean isMinimumInputLengthSet() {
    return getStateHelper().eval(Select2Keys.minimumInputLength) != null;
  }

  public void setMinimumInputLength(int minimumInputLength) {
    getStateHelper().put(Select2Keys.minimumInputLength, minimumInputLength);
  }

  public int getMaximumSelectionLength() {
    Integer maximumSelectionLength = (Integer) getStateHelper().eval(Select2Keys.maximumSelectionLength);
    if (maximumSelectionLength != null) {
      return maximumSelectionLength;
    }
    return 0;
  }
  public boolean isMaximumSelectionLengthSet() {
    return getStateHelper().eval(Select2Keys.maximumSelectionLength) != null;
  }

  public void setMaximumSelectionLength(int maximumSelectionLength) {
    getStateHelper().put(Select2Keys.maximumSelectionLength, maximumSelectionLength);
  }

  public void setMinimumResultsForSearch(int minimumResultsForSearch) {
    getStateHelper().put(Select2Keys.minimumResultsForSearch, minimumResultsForSearch);
  }

  public int getMinimumResultsForSearch() {
    Integer minimumResultsForSearch = (Integer) getStateHelper().eval(Select2Keys.minimumResultsForSearch);
    if (minimumResultsForSearch != null) {
      return minimumResultsForSearch;
    }
    return 20;
  }

  public boolean isMinimumResultsForSearchSet() {
    return getStateHelper().eval(Select2Keys.minimumResultsForSearch) != null;
  }


}
