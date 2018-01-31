// The author disclaims copyright to this source code.
package nl.jvdploeg.flat.app;

import org.junit.Test;

import nl.jvdploeg.flat.app.impl.DefaultApplication;

public class DefaultApplicationTest {

  @Test(expected = IllegalStateException.class)
  public void testStartWithoutSettings() {
    final DefaultApplication application = new DefaultApplication();
    application.start();
  }
}
