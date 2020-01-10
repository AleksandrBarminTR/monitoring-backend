package ru.mydesignstudio.monitor.component.jenkins.service.job;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJobStatus;
import ru.mydesignstudio.monitor.component.jenkins.service.JenkinsJobStatusChecker;
import ru.mydesignstudio.monitor.component.jenkins.service.JenkinsService;

@ExtendWith(MockitoExtension.class)
class NotCompletedJobsCheckerTest {
  @Mock
  private JenkinsService jenkinsService;
  @Mock
  private NotCompletedJobsProvider jobsProvider;
  @Mock
  private JenkinsJobStatusChecker statusChecker;
  @InjectMocks
  private NotCompletedJobsChecker unitUnderTest;

  @Test
  void check_everythingStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void execute_jobsShouldBeRequested() {
    unitUnderTest.execute();

    verify(jobsProvider, times(1)).provide();
  }

  @Test
  void execute_shouldCheckEveryJobStatus() {
    final JenkinsJob jenkinsJob = mock(JenkinsJob.class);
    when(jobsProvider.provide()).thenReturn(Collections.singletonList(jenkinsJob));

    unitUnderTest.execute();

    verify(statusChecker, times(1)).check(any(JenkinsJob.class));
  }

  @Test
  void execute_updatedJobsShouldBeSaved() {
    final JenkinsJob jenkinsJob = new JenkinsJob();
    jenkinsJob.setStatus(JenkinsJobStatus.NOT_STARTED);

    when(jobsProvider.provide()).thenReturn(Collections.singletonList(jenkinsJob));
    when(statusChecker.check(jenkinsJob)).thenReturn(JenkinsJobStatus.IN_PROGRESS);

    final ArgumentCaptor<JenkinsJob> savedJobCaptor = ArgumentCaptor
        .forClass(JenkinsJob.class);

    unitUnderTest.execute();

    verify(jenkinsService, times(1)).save(savedJobCaptor.capture());

    final JenkinsJob savedJob = savedJobCaptor.getValue();

    assertAll(
        () -> assertNotNull(savedJob),
        () -> assertEquals(JenkinsJobStatus.IN_PROGRESS, savedJob.getStatus())
    );
  }

  @Test
  void execute_notChangedJobsShouldNotBeSaved() {
    final JenkinsJob jenkinsJob = new JenkinsJob();
    jenkinsJob.setStatus(JenkinsJobStatus.IN_PROGRESS);

    when(jobsProvider.provide()).thenReturn(Collections.singletonList(jenkinsJob));
    when(statusChecker.check(jenkinsJob)).thenReturn(JenkinsJobStatus.IN_PROGRESS);

    unitUnderTest.execute();

    verify(jenkinsService, times(0)).save(any(JenkinsJob.class));
  }
}