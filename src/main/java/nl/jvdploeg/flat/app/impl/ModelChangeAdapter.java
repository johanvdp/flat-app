// The author disclaims copyright to this source code.
package nl.jvdploeg.flat.app.impl;

import java.util.List;

import nl.jvdploeg.exception.Checks;
import nl.jvdploeg.flat.Change;
import nl.jvdploeg.flat.Model;
import nl.jvdploeg.flat.Node;
import nl.jvdploeg.flat.Path;
import nl.jvdploeg.flat.Version;
import nl.jvdploeg.flat.impl.DefaultChange;

@SuppressWarnings("rawtypes")
public final class ModelChangeAdapter implements Model {

  private static final String TYPE_NAME = ModelChangeAdapter.class.getSimpleName();
  private final Model<?> model;
  private final List<Change> changes;

  /**
   * Create a wrapper that collects changes.
   *
   * @param model
   *          The model wrapped.
   * @param changes
   *          The change collection.
   */
  public ModelChangeAdapter(final Model<?> model, final List<Change> changes) {
    Checks.ARGUMENT.notNull(model, "model");
    Checks.ARGUMENT.notNull(changes, "changes");
    this.model = model;
    this.changes = changes;
  }

  @Override
  public void add(final Path path) {
    changes.add(DefaultChange.add(path));
    model.add(path);
  }

  @Override
  public Node createChild(final Path path) {
    final Node child = model.createChild(path);
    changes.add(DefaultChange.add(path));
    return child;
  }

  @Override
  public Node findNode(final Path path) {
    return model.findNode(path);
  }

  @Override
  public String getName() {
    return model.getName();
  }

  @Override
  public Node getNode(final Path path) {
    return model.getNode(path);
  }

  @Override
  public Node getRoot() {
    return model.getRoot();
  }

  @Override
  public String getValue(final Path path) {
    return model.getValue(path);
  }

  @Override
  public Version getVersion(final Path path) {
    return model.getVersion(path);
  }

  @Override
  public void remove(final Path path) {
    changes.add(DefaultChange.remove(path));
    model.remove(path);
  }

  @Override
  public String setValue(final Path path, final String newValue) {
    final Version oldVersion = model.getVersion(path);
    final String oldValue = model.setValue(path, newValue);
    changes.add(DefaultChange.set(path, oldVersion, newValue));
    return oldValue;
  }

  @Override
  public Version setVersion(final Path path, final Version newVersion) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String toString() {
    return TYPE_NAME + "[changes=" + changes + ",model=" + model + "]";
  }
}
