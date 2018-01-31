// The author disclaims copyright to this source code.
package nl.jvdploeg.flat.app.impl;

import java.util.function.Supplier;

import nl.jvdploeg.flat.Version;
import nl.jvdploeg.flat.impl.NumberedVersion;

public final class SequentialVersionFactory implements Supplier<Version> {

  private long counter;

  public SequentialVersionFactory() {
  }

  @Override
  public Version get() {
    counter += 1;
    return new NumberedVersion(counter);
  }
}
