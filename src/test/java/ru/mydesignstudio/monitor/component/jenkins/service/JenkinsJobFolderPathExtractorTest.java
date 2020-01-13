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
class JenkinsJobFolderPathExtractorTest {
  @Mock
  private Repository repository;
  @InjectMocks
  private JenkinsJobFolderPathExtractor unitUnderTest;

  @Test
  void check_everythingStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void extract_shouldExtractWithoutHost() throws Exception {
    when(repository.getJobLink()).thenReturn(new URL(
        "http://ukmy-dvm-53.tlr.thomson.com:8080/job/BUILD/job/COMMON/job/SERVICE-COMMON/job/BUILD-MR/"));

    final String path = unitUnderTest.extract(repository);

    assertAll(
        () -> assertNotNull(path),
        () -> assertEquals("BUILD/COMMON/SERVICE-COMMON", path)
    );
  }
}