// The author disclaims copyright to this source code.
package nl.jvdploeg.flat.app.impl;

import java.util.function.Supplier;

import nl.jvdploeg.flat.Version;

public final class TimestampVersionFactory implements Supplier<Version> {

  private long previousTimestamp;
  private long previousSequence;

  public TimestampVersionFactory() {
  }

  @Override
  public Version get() {
    final long timestamp = System.currentTimeMillis();
    if (timestamp == previousTimestamp) {
      previousSequence += 1;
    } else {
      previousTimestamp = timestamp;
      previousSequence = 1;
    }
    return new TimestampVersion(timestamp, previousSequence);
  }
}
