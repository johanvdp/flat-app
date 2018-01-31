// The author disclaims copyright to this source code.
package nl.jvdploeg.flat.app;

import java.util.List;

import nl.jvdploeg.flat.Change;
import nl.jvdploeg.flat.Model;

/**
 * The rule(s) determine the consequences of changes performed on the model.
 */
public interface Rule {

  static Rule always(final List<Job> consequences) {
    return (model, changes) -> consequences;
  }

  /**
   * Get consequences of changes performed on the model.
   */
  List<Job> getConsequences(Model<?> model, List<Change> changes);
}
