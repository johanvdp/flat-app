// The author disclaims copyright to this source code.
package nl.jvdploeg.flat.app;

import java.util.List;

import nl.jvdploeg.flat.Change;
import nl.jvdploeg.flat.Model;

/**
 * The validation rule(s) determine if the changes performed on the model are
 * valid.
 */
public interface Validation {

  interface ValidationResult {

    boolean isValid();
  }

  /**
   * Evaluate the changes performed on the model.
   */
  ValidationResult evaluate(Model<?> model, List<Change> changes);
}
