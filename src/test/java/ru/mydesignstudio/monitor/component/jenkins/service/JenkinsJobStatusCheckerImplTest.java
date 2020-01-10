package ru.mydesignstudio.monitor.component.jenkins.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.cdancy.jenkins.rest.domain.job.BuildInfo;
import com.cdancy.jenkins.rest.features.JobsApi;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJobStatus;

@ExtendWith(MockitoExtension.class)
class JenkinsJobStatusCheckerImplTest {
  @Mock
  private JobsApi jobsApi;
  @Mock
  private BuildInfo buildInfo;
  @Mock
  private JenkinsJob jenkinsJob;
  @InjectMocks
  private JenkinsJobStatusCheckerImpl unitUnderTest;

  @Test
  void check_everythingStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void check_shouldCheckInputParameters() {
    assertThrows(NullPointerException.class, () -> unitUnderTest.check(null));
  }

  @ParameterizedTest
  @CsvSource(value = {
      "SUCCESS,SUCCESS",
      "FAILURE,FAILED",
      ",IN_PROGRESS"
  })
  void check_shouldReturnJobStatus(String jenkinsResponse, JenkinsJobStatus jobStatus) {
    when(jenkinsJob.getJobName()).thenReturn(RandomStringUtils.randomAlphabetic(10));
    when(jenkinsJob.getJobFolder()).thenReturn(RandomStringUtils.randomAlphabetic(10));
    when(jenkinsJob.getBuildNumber()).thenReturn(RandomUtils.nextInt());
    when(jobsApi.buildInfo(anyString(), anyString(), anyInt())).thenReturn(buildInfo);
    when(buildInfo.result()).thenReturn(jenkinsResponse);

    final JenkinsJobStatus status = unitUnderTest.check(jenkinsJob);

    assertAll(
        () -> assertNotNull(status),
        () -> assertEquals(jobStatus, status)
    );
  }

  @Test
  @Disabled
  void check_inCaseOf404ShouldBeInProgress() {
    when(jenkinsJob.getJobName()).thenReturn(RandomStringUtils.randomAlphabetic(10));
    when(jenkinsJob.getJobFolder()).thenReturn(RandomStringUtils.randomAlphabetic(10));
    when(jenkinsJob.getBuildNumber()).thenReturn(RandomUtils.nextInt());
    when(jobsApi.buildInfo(anyString(), anyString(), anyInt())).thenReturn(buildInfo);
  }
}