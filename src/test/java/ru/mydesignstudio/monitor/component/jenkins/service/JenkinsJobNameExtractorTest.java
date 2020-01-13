package ru.mydesignstudio.monitor.component.jenkins.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.net.URL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;

@ExtendWith(MockitoExtension.class)
class JenkinsJobNameExtractorTest {
  @Mock
  private Repository repository;
  @InjectMocks
  private JenkinsJobNameExtractor unitUnderTest;

  @Test
  void check_everythingStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void extract_shouldExtractName() throws Exception {
    when(repository.getJobLink()).thenReturn(new URL(
        "http://ukmy-dvm-53.tlr.thomson.com:8080/job/BUILD/job/COMMON/job/SERVICE-COMMON/job/BUILD-MR/"));

    final String jobName = unitUnderTest.extract(repository);

    assertAll(
        () -> assertNotNull(jobName),
        () -> assertEquals("BUILD-MR", jobName)
    );
  }
}