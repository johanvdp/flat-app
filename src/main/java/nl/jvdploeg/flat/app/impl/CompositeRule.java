// The author disclaims copyright to this source code.
package nl.jvdploeg.flat.app.impl;

import java.util.ArrayList;
import java.util.List;

import nl.jvdploeg.flat.Change;
import nl.jvdploeg.flat.Model;
import nl.jvdploeg.flat.app.Job;
import nl.jvdploeg.flat.app.Rule;

/**
 * Composite of {@link Rule}s.
 */
public final class CompositeRule implements Rule {

  private final List<Rule> rules = new ArrayList<>();

  public CompositeRule() {
  }

  public void add(final Rule rule) {
    rules.add(rule);
  }

  @Override
  public List<Job> getConsequences(final Model<?> model, final List<Change> changes) {
    final List<Job> consequences = new ArrayList<>();
    for (final Rule rule : rules) {
      final List<Job> ruleConsequences = rule.getConsequences(model, changes);
      consequences.addAll(ruleConsequences);
    }
    return consequences;
  }

  public void remove(final Rule rule) {
    rules.remove(rule);
  }
}
