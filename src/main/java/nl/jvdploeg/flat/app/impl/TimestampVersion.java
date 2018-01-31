// The author disclaims copyright to this source code.
package nl.jvdploeg.flat.app.impl;

import nl.jvdploeg.flat.Version;

public final class TimestampVersion implements Version {

  private static final String TYPE_NAME = TimestampVersion.class.getSimpleName();
  private final long timestamp;
  private final long sequence;

  public TimestampVersion(final long timestamp, final long sequence) {
    this.timestamp = timestamp;
    this.sequence = sequence;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final TimestampVersion other = (TimestampVersion) obj;
    if (timestamp != other.timestamp) {
      return false;
    }
    if (sequence != other.sequence) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 113;
    int result = 1;
    result = prime * result + (int) (timestamp ^ timestamp >>> 32);
    result = prime * result + (int) (sequence ^ sequence >>> 32);
    return result;
  }

  @Override
  public String toString() {
    return TYPE_NAME + "[timestamp=" + timestamp + ",sequence=" + sequence + "]";
  }
}
