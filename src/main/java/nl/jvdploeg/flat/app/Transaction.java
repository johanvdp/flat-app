// The author disclaims copyright to this source code.
package nl.jvdploeg.flat.app;

import java.util.List;

import nl.jvdploeg.flat.Change;
import nl.jvdploeg.flat.Model;

/**
 * A transaction defines a shielded environment.<br>
 * All jobs executed in the same transaction see each others changes to the
 * model.
 * <ol>
 * <li>{@link #open(Model)}
 * <li>{@link #execute(Job)}
 * <li>{@link #getModel()} (optional)
 * <li>{@link #getChanges()}
 * <li>{@link #close()}
 */
public interface Transaction extends AutoCloseable {

  /**
   * End transaction.
   */
  @Override
  void close();

  /**
   * Execute {@link Job} within transaction.
   *
   * @param job
   *          The job.
   */
  void execute(Job job);

  /**
   * Access changes to the model that where collected during {@link Job}
   * execution.
   *
   * @return The changes.
   */
  List<Change> getChanges();

  /**
   * Access the model visible within the transaction. Accessed by the jobs
   * executing within the transaction.
   *
   * @see #open(Model)
   */
  Model<?> getModel();

  /**
   * Begin transaction.
   *
   * @param model
   *          The model visible within the transaction.
   */
  void open(Model<?> model);
}
