package ru.mydesignstudio.monitor.component.jenkins.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJobStatus;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;

@ExtendWith(MockitoExtension.class)
class JenkinsJobFactoryTest {
  @Mock
  private JenkinsJobFolderPathExtractor jobFolderPathExtractor;
  @Mock
  private JenkinsJobNameExtractor jobNameExtractor;
  @Mock
  private PullRequest pullRequest;
  @Mock
  private Repository repository;
  @InjectMocks
  private JenkinsJobFactory unitUnderTest;

  @Test
  void check_everythingWorks() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void create_shouldCheckInputArguments() {
    assertThrows(NullPointerException.class, () -> {
      unitUnderTest.create(null, null);
    });
  }

  @Test
  void create_shouldGenerateJenkinsJob() {
    when(pullRequest.getRepository()).thenReturn(repository);

    final JenkinsJob job = unitUnderTest.create(pullRequest, 42);

    assertAll(
        () -> assertNotNull(job),
        () -> assertEquals(JenkinsJobStatus.NOT_STARTED, job.getStatus()),
        () -> assertEquals(pullRequest.getUrl(), job.getPullRequest()),
        () -> assertEquals(42, job.getQueueNumber()),
        () -> assertNotNull(job.getCreated())
    );
  }
}