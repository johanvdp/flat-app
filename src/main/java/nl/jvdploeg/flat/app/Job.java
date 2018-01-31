// The author disclaims copyright to this source code.
package nl.jvdploeg.flat.app;

import java.util.List;

import nl.jvdploeg.flat.Change;
import nl.jvdploeg.flat.Model;

/**
 * Job
 * <ol>
 * <li>First {@link #execute(Model)}
 * <li>Then retrieve resulting {@link #getChanges()}, and {@link #getJobs()}
 */
public interface Job {

  /** Execute using the provided {@link Model}. */
  void execute(Model<?> model);

  /** Get resulting {@link Change}s. */
  List<Change> getChanges();

  /** Get resulting {@link Job}s. */
  List<Job> getJobs();
}
