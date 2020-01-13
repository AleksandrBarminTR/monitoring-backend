package ru.mydesignstudio.monitor.component.jenkins.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;

@ExtendWith(MockitoExtension.class)
class JenkinsJobParametersBuilderTest {
  @Mock
  private PullRequest pullRequest;
  @InjectMocks
  private JenkinsJobParametersBuilder unitUnderTest;

  @Test
  void check_everythingStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void build_shouldReturnHeadSha() {
    final String headSha = RandomStringUtils.randomAlphabetic(10);
    when(pullRequest.getHeadHash()).thenReturn(headSha);

    final Map<String, List<String>> params = unitUnderTest.build(pullRequest);

    assertAll(
        () -> assertNotNull(params),
        () -> assertNotNull(params.get("HEAD_HASH")),
        () -> assertFalse(params.get("HEAD_HASH").isEmpty()),
        () -> assertEquals(1, params.get("HEAD_HASH").size()),
        () -> assertEquals(headSha, params.get("HEAD_HASH").get(0))
    );
  }

  @Test
  void build_shouldCheckInputParameters() {
    assertThrows(NullPointerException.class, () -> unitUnderTest.build(null));
  }
}