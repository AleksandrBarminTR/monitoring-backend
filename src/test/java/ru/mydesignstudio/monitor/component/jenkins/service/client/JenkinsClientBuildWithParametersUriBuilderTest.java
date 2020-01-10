package ru.mydesignstudio.monitor.component.jenkins.service.client;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.net.URL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JenkinsClientBuildWithParametersUriBuilderTest {
  @InjectMocks
  private JenkinsClientBuildWithParametersUriBuilder unitUnderTest;

  @Test
  void check_everythingStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void build_shouldBuildAppropriateURI() throws Exception {
    final URL jenkinsHost = new URL("http://localhost:8080/");

    final URI uri = unitUnderTest.build(jenkinsHost, "folder1/folder2/folder3", "my-job");

    final URI targetUri = URI.create(
        "http://localhost:8080/job/folder1/job/folder2/job/folder3/job/my-job/buildWithParameters");

    assertAll(
        () -> assertNotNull(uri),
        () -> assertEquals(targetUri, uri)
    );
  }

  @Test
  void build_inputParametersShouldBeChecked() {
    assertThrows(NullPointerException.class, () -> unitUnderTest.build(null, null, null));
  }
}
