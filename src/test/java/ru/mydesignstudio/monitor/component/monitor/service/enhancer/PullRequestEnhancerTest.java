package ru.mydesignstudio.monitor.component.monitor.service.enhancer;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.net.URL;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorResponse;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorResponse.MonitorResponseBuilder;
import ru.mydesignstudio.monitor.component.pull.request.model.GitHubStatus;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;

@ExtendWith(MockitoExtension.class)
class PullRequestEnhancerTest {

  @Mock
  private PullRequest request;
  @InjectMocks
  private PullRequestEnhancer unitUnderTest;

  @Test
  void enhance_populateNecessaryFields() throws Exception {
    final String title = RandomStringUtils.randomAlphabetic(10);
    final String url = "http://" + RandomStringUtils.randomAlphabetic(10);

    final MonitorResponseBuilder builder = MonitorResponse.builder();
    when(request.getTitle()).thenReturn(title);
    when(request.getUrl()).thenReturn(new URL(url));
    when(request.getStatus()).thenReturn(GitHubStatus.OPEN);

    unitUnderTest.enhance(builder, request);

    final MonitorResponse response = builder.build();

    assertAll(
        () -> assertNotNull(builder),
        () -> assertEquals(url, response.getGithubLink()),
        () -> assertEquals(title, response.getGithubTitle()),
        () -> assertEquals(GitHubStatus.OPEN, response.getGithubStatus())
    );
  }
}