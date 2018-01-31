// The author disclaims copyright to this source code.
package nl.jvdploeg.flat.app.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.jvdploeg.exception.Checks;
import nl.jvdploeg.flat.Change;
import nl.jvdploeg.flat.Model;
import nl.jvdploeg.flat.app.Job;
import nl.jvdploeg.flat.app.Transaction;
import nl.jvdploeg.object.Instance;

/**
 * A transaction defines a shielded environment.<br>
 * Any jobs created during job execution are executed in the same transaction in
 * depth first order against the same model.
 */
public final class DefaultTransaction implements Transaction {

  private static final Logger LOG = LoggerFactory.getLogger(DefaultTransaction.class);

  /**
   * collection of changes during job execution.
   */
  private final List<Change> changes = new ArrayList<>();

  /**
   * The model.
   */
  private Model<?> model;

  /**
   * Constructor.
   */
  public DefaultTransaction() {
  }

  @Override
  public void close() {
    LOG.info(">< close()");
    changes.clear();
    model = null;
  }

  @Override
  public void execute(final Job theJob) {
    LOG.info("> execute(job={})", Instance.identity(theJob));
    if (model == null) {
      throw new IllegalStateException("model is null (begin transaction first)");
    }
    // start depth first job execution
    executeDepthFirst(null, theJob);
    LOG.info("< execute");
  }

  @Override
  public List<Change> getChanges() {
    return changes;
  }

  @Override
  public Model<?> getModel() {
    return model;
  }

  @Override
  public void open(final Model<?> theModel) {
    LOG.info(">< open(model={})", Instance.identity(theModel));
    Checks.ARGUMENT.notNull(theModel, "theModel");
    model = theModel;
  }

  /**
   * Depth first {@link Job} execution.<br>
   * Any jobs created during job execution are also executed in depth first
   * order.
   */
  private void executeDepthFirst(final Job parent, final Job job) {
    LOG.info("> executeDepthFirst(parent={},job={})", Instance.identity(parent), Instance.identity(job));
    job.execute(model);
    final List<Change> jobChanges = job.getChanges();
    changes.addAll(jobChanges);
    final List<Job> childJobs = job.getJobs();
    for (final Job child : childJobs) {
      executeDepthFirst(job, child);
    }
    LOG.info("< executeDepthFirst");
  }
}
