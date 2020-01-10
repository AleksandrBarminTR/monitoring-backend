package ru.mydesignstudio.monitor.component.monitor.service.enhancer;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJobStatus;
import ru.mydesignstudio.monitor.component.jenkins.service.JenkinsService;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorResponse;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorResponse.MonitorResponseBuilder;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;
import ru.mydesignstudio.monitor.component.test.utils.RandomUrlUtils;

@ExtendWith(MockitoExtension.class)
class JenkinsEnhancerTest {
  private final String headSha = RandomStringUtils.randomAlphabetic(10);

  @InjectMocks
  private RandomUrlUtils randomUrlUtils;
  @Mock
  private PullRequest pullRequest;
  @Mock
  private JenkinsService jenkinsService;
  @InjectMocks
  private JenkinsEnhancer unitUnderTest;

  @Test
  void check_everythingStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void enhance_shouldSetPropertyIfJobNotFound() {
    when(jenkinsService.findOneByHeadSha(anyString())).thenReturn(Optional.empty());
    when(pullRequest.getHeadHash()).thenReturn(headSha);

    final MonitorResponseBuilder builder = MonitorResponse.builder();
    builder.buildResolved(true); // set the true value to break the test

    unitUnderTest.enhance(builder, pullRequest);

    final MonitorResponse response = builder.build();

    assertAll(
        () -> assertNotNull(response),
        () -> assertFalse(response.isBuildResolved())
    );
  }

  @Test
  void enhance_shouldHaveLinkAndOtherPropertiesIfJobFound() throws Exception {
    final JenkinsJob jenkinsJob = mock(JenkinsJob.class);
    final String jobUrl = randomUrlUtils.randomUrlString(10);

    when(jenkinsService.findOneByHeadSha(headSha)).thenReturn(Optional.of(jenkinsJob));
    when(pullRequest.getHeadHash()).thenReturn(headSha);
    when(jenkinsJob.getBuildUrl()).thenReturn(new URL(jobUrl));
    when(jenkinsJob.getStatus()).thenReturn(JenkinsJobStatus.NOT_STARTED);

    final MonitorResponseBuilder builder = MonitorResponse.builder();

    unitUnderTest.enhance(builder, pullRequest);

    final MonitorResponse response = builder.build();

    assertAll(
        () -> assertNotNull(response),
        () -> assertTrue(response.isBuildResolved()),
        () -> assertEquals(jobUrl, response.getBuildLink()),
        () -> assertEquals(JenkinsJobStatus.NOT_STARTED, response.getBuildStatus())
    );
  }
}
