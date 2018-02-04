// The author disclaims copyright to this source code.
package nl.jvdploeg.flat.app;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import nl.jvdploeg.flat.Model;
import nl.jvdploeg.flat.Path;
import nl.jvdploeg.flat.Version;
import nl.jvdploeg.flat.app.impl.DefaultApplication;

public class DefaultApplicationTest {

  @Mocked
  private Model<?> model;
  @Mocked
  private Model<?> clonedModel;
  @Mocked
  private Validation validation;
  @Mocked
  private Rule rule;
  @Mocked
  private Job job;

  @Test(expected = IllegalStateException.class)
  public void testStartWithoutSettings() {
    // given
    final DefaultApplication application = new DefaultApplication();
    // when
    application.start();
    // then
    Assert.fail("exception expected");
  }

  @Test
  public void testStartWithSettings() {
    // given
    final DefaultApplication application = new DefaultApplication();
    applyMockedSettings(application);
    // when
    application.start();
    // then ok
  }

  @Test
  public void testSettingsRemembered() {
    // given
    final DefaultApplication application = new DefaultApplication();
    applyMockedSettings(application);
    // when
    final Model<?> actualModel = application.getModel();
    // then
    // check instance, not contents
    Assert.assertTrue(model == actualModel);
  }

  @Test
  public void testExecuteJobWithoutEffect() {
    // given
    final DefaultApplication application = new DefaultApplication();
    applyMockedSettings(application);
    application.start();
    expectJobWithoutEffect();
    // when
    application.execute(job);
    // then
    verifyNoModelEffect();
  }

  @SuppressWarnings("unused")
  private void expectJobWithoutEffect() {
    new Expectations() {

      {
        // job executed on cloned model
        job.execute(clonedModel);
        // job execution has no effects
        job.getChanges();
        maxTimes = 1;
        result = new ArrayList<>();
        job.getJobs();
        maxTimes = 1;
        result = new ArrayList<>();
      }
    };
  }

  private void applyMockedSettings(final DefaultApplication application) {
    application.setModel(model);
    application.setValidation(validation);
    application.setRule(rule);
    application.setCloneModelMethod(t -> clonedModel);
    // keep default transaction factory
  }

  @SuppressWarnings("unused")
  private void verifyNoModelEffect() {
    new Verifications() {

      {
        model.setValue((Path) any, anyString);
        maxTimes = 0;
        model.setVersion((Path) any, (Version) any);
        maxTimes = 0;
        model.createChild((Path) any);
        maxTimes = 0;
        model.add((Path) any);
        maxTimes = 0;
        model.remove((Path) any);
        maxTimes = 0;
      }
    };
  }
}
