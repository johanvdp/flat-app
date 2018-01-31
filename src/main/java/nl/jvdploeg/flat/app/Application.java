// The author disclaims copyright to this source code.
package nl.jvdploeg.flat.app;

import java.util.function.Supplier;

import nl.jvdploeg.flat.Model;

/**
 * Application
 * <ol>
 * <li>First, configure the application (use setters).
 * <li>Then {@link #start()} the application.
 * <li>Perform (multiple) {@link #execute(Job)}s during the lifetime of the
 * application.
 * <li>And finally {@link #stop()} the application.
 */
public interface Application {

  /**
   * Execute {@link Job} during the lifetime of the application.
   */
  void execute(Job job);

  /**
   * Get the model set earlier.<br>
   * Convenience method for those who have no access to the creation process.
   *
   * @return The model.
   */
  Model<?> getModel();

  /**
   * Set the model before {@link #start()} of the the application.
   */
  void setModel(Model<?> model);

  /**
   * Set the rule before {@link #start()} of the the application.
   */
  void setRule(Rule rule);

  /**
   * Set the transaction factory before {@link #start()} of the the application.
   */
  void setTransactionFactory(Supplier<Transaction> transactionFactory);

  /**
   * Set the validation before {@link #start()} of the the application.
   */
  void setValidation(Validation validation);

  /**
   * Start the application.
   */
  void start();

  /**
   * Stop the application.
   */
  void stop();
}
