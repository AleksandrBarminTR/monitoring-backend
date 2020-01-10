package ru.mydesignstudio.monitor.component.jenkins.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.service.client.JenkinsClient;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;

@ExtendWith(MockitoExtension.class)
class JenkinsJobStarterImplTest {
  @Mock
  private JenkinsJobFactory jobFactory;
  @Mock
  private JenkinsJobNameExtractor jobNameExtractor;
  @Mock
  private JenkinsJobFolderPathExtractor jobFolderPathExtractor;
  @Mock
  private PullRequest pullRequest;
  @Mock
  private Repository repository;
  @Mock
  private JenkinsJobParametersBuilder parametersBuilder;
  @Mock
  private JenkinsClient jenkinsClient;
  @InjectMocks
  private JenkinsJobStarterImpl unitUnderTest;

  @Test
  void check_everythingStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void start_shouldBuildJobParametersAndThenStartTheJob() {
    final String jobName = RandomStringUtils.randomAlphabetic(10);
    final String jobFolder = RandomStringUtils.randomAlphabetic(10);
    final Map<String, List<String>> params = mock(Map.class);
    final JenkinsJob jenkinsJob = mock(JenkinsJob.class);

    when(pullRequest.getRepository()).thenReturn(repository);
    when(jobNameExtractor.extract(repository)).thenReturn(jobName);
    when(jobFolderPathExtractor.extract(repository)).thenReturn(jobFolder);
    when(parametersBuilder.build(pullRequest)).thenReturn(params);
    when(jobFactory.create(any(PullRequest.class), anyInt())).thenReturn(jenkinsJob);
    when(jenkinsClient.build(anyString(), anyString(), any(Map.class))).thenReturn(42);

    final JenkinsJob startedJob = unitUnderTest.start(pullRequest);

    verify(parametersBuilder, times(1)).build(pullRequest);
    verify(jobNameExtractor, times(1)).extract(repository);
    verify(jobFolderPathExtractor, times(1)).extract(repository);

    final ArgumentCaptor<String> jobNameCaptor = ArgumentCaptor.forClass(String.class);
    final ArgumentCaptor<String> jobFolderCaptor = ArgumentCaptor.forClass(String.class);
    final ArgumentCaptor<Map> paramsCaptor = ArgumentCaptor.forClass(Map.class);

    verify(jenkinsClient, times(1))
        .build(jobFolderCaptor.capture(), jobNameCaptor.capture(),
            paramsCaptor.capture());

    assertAll(
        () -> assertEquals(jobName, jobNameCaptor.getValue()),
        () -> assertEquals(jobFolder, jobFolderCaptor.getValue()),
        () -> assertEquals(params, paramsCaptor.getValue()),
        () -> assertNotNull(startedJob)
    );
  }

  @Test
  void start_shouldCheckInputParameters() {
    assertThrows(NullPointerException.class, () -> {
      unitUnderTest.start(null);
    });
  }
}