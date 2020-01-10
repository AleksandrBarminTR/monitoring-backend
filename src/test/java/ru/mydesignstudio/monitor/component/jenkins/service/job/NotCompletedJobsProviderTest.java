package ru.mydesignstudio.monitor.component.jenkins.service.job;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJobStatus;
import ru.mydesignstudio.monitor.component.jenkins.service.JenkinsService;

@ExtendWith(MockitoExtension.class)
class NotCompletedJobsProviderTest {
  @Mock
  private JenkinsService jenkinsService;
  @InjectMocks
  private NotCompletedJobsProvider unitUnderTest;

  @Test
  void provide_shouldReturnJobs() {
    when(jenkinsService.findJobsByStatus(any(JenkinsJobStatus.class))).thenAnswer(invocationOnMock -> {
      final JenkinsJobStatus status = invocationOnMock.getArgument(0);
      final JenkinsJob job = mock(JenkinsJob.class);

      when(job.getStatus()).thenReturn(status);

      return Collections.singletonList(job);
    });

    final List<JenkinsJob> jobs = unitUnderTest.provide();

    assertAll(
        () -> assertNotNull(jobs),
        () -> assertFalse(jobs.isEmpty()),
        () -> assertTrue(
            jobs.stream()
            .anyMatch(job -> JenkinsJobStatus.NOT_STARTED == job.getStatus())
        ),
        () -> assertTrue(
            jobs.stream()
            .anyMatch(job -> JenkinsJobStatus.IN_PROGRESS == job.getStatus())
        ),
        () -> assertFalse(
            jobs.stream()
            .anyMatch(job -> JenkinsJobStatus.FAILED == job.getStatus())
        ),
        () -> assertFalse(
            jobs.stream()
            .anyMatch(job -> JenkinsJobStatus.SUCCESS == job.getStatus())
        )
    );
  }
}