// The author disclaims copyright to this source code.
package nl.jvdploeg.flat.app.impl;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import nl.jvdploeg.exception.Checks;
import nl.jvdploeg.flat.Change;
import nl.jvdploeg.flat.Model;
import nl.jvdploeg.flat.app.Application;
import nl.jvdploeg.flat.app.Job;
import nl.jvdploeg.flat.app.Rule;
import nl.jvdploeg.flat.app.Transaction;
import nl.jvdploeg.flat.app.Validation;
import nl.jvdploeg.flat.app.Validation.ValidationResult;
import nl.jvdploeg.flat.impl.DefaultModel;
import nl.jvdploeg.flat.impl.Enforce;
import nl.jvdploeg.flat.util.ModelUtils;

public final class DefaultApplication implements Application {

  public static UnaryOperator<Model<?>> createDefaultCloneModelMethod() {
    return model -> {
      final Model<?> clone = new DefaultModel(model.getName() + "-clone", Enforce.STRICT, model);
      return clone;
    };
  }

  public static Supplier<Transaction> createDefaultTransactionFactory() {
    return () -> {
      final Transaction transaction = new DefaultTransaction();
      return transaction;
    };
  }

  private Model<?> model;
  private Validation validation;
  private Rule rule;

  private UnaryOperator<Model<?>> cloneModelMethod = createDefaultCloneModelMethod();
  private Supplier<Transaction> transactionFactory = createDefaultTransactionFactory();

  public DefaultApplication() {
  }

  @Override
  public void execute(final Job job) {
    // isolate job execution in a separate transaction
    final Model<?> clone = cloneModelMethod.apply(model);
    try (Transaction transaction = transactionFactory.get()) {
      transaction.open(clone);
      // execute jobs, consequences, and validate changes
      final boolean valid = execute(Arrays.asList(job), transaction);
      if (valid) {
        // apply changes to actual model
        final List<Change> changes = transaction.getChanges();
        ModelUtils.applyChanges(model, changes);
      }
    }
  }

  @Override
  public Model<?> getModel() {
    return model;
  }

  public void setCloneModelMethod(final UnaryOperator<Model<?>> cloneModelMethod) {
    this.cloneModelMethod = cloneModelMethod;
  }

  @Override
  public void setModel(final Model<?> model) {
    this.model = model;
  }

  @Override
  public void setRule(final Rule rule) {
    this.rule = rule;
  }

  @Override
  public void setTransactionFactory(final Supplier<Transaction> transactionFactory) {
    this.transactionFactory = transactionFactory;
  }

  @Override
  public void setValidation(final Validation validation) {
    this.validation = validation;
  }

  @Override
  public void start() {
    Checks.STATE.notNull(model, "model");
    Checks.STATE.notNull(validation, "validation");
    Checks.STATE.notNull(rule, "rule");
    Checks.STATE.notNull(cloneModelMethod, "cloneModelMethod");
    Checks.STATE.notNull(transactionFactory, "transactionFactory");
  }

  @Override
  public void stop() {
  }

  private boolean execute(final List<Job> jobs, final Transaction transaction) {
    final Model<?> clone = transaction.getModel();
    // execute jobs in same transaction
    for (final Job job : jobs) {
      transaction.execute(job);
    }
    // validate changes after job execution
    final List<Change> changes = transaction.getChanges();
    final ValidationResult result = validation.evaluate(clone, changes);
    if (!result.isValid()) {
      // abort job execution
      return false;
    }
    // check rules for consequences
    final List<Job> consequences = rule.getConsequences(clone, changes);
    // execute consequences, which might be recursive
    if (consequences.size() > 0) {
      return execute(consequences, transaction);
    }
    return true;
  }
}
