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

  /** Mainly for testing. */
  static Validation always(final boolean valid) {
    return (model, changes) -> valid;
  }

  /**
   * Check if the changes performed on the model are valid.
   */
  boolean isValid(Model<?> model, List<Change> changes);
}
