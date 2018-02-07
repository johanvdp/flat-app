// The author disclaims copyright to this source code.
package nl.jvdploeg.flat.app.impl;

import java.util.ArrayList;
import java.util.List;

import nl.jvdploeg.flat.Change;
import nl.jvdploeg.flat.Model;
import nl.jvdploeg.flat.app.Validation;

/**
 * Composite of {@link Validation}s.
 */
public class CompositeValidation implements Validation {

  private final List<Validation> validators = new ArrayList<>();

  public CompositeValidation() {
  }

  public final void add(final Validation validator) {
    validators.add(validator);
  }

  @Override
  public final ValidationResult evaluate(final Model<?> model, final List<Change> changes) {
    final DefaultValidationResult result = new DefaultValidationResult();
    for (final Validation validator : validators) {
      final ValidationResult oneResult = validator.evaluate(model, changes);
      result.merge(oneResult);
    }
    return result;
  }

  public final void remove(final Validation validator) {
    validators.remove(validator);
  }
}
