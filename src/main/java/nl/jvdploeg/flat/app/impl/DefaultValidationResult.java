package nl.jvdploeg.flat.app.impl;

import nl.jvdploeg.flat.app.Validation;
import nl.jvdploeg.flat.app.Validation.ValidationResult;

public final class DefaultValidationResult implements Validation.ValidationResult {

  private boolean valid = true;

  public DefaultValidationResult() {
  }

  @Override
  public boolean isValid() {
    return valid;
  }

  public void setValid(final boolean valid) {
    this.valid = valid;
  }

  public void merge(final ValidationResult other) {
    if (!other.isValid()) {
      valid = false;
    }
  }
}
