package ru.mydesignstudio.monitor.component.pull.request.service.github;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHUser;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;

@ExtendWith(MockitoExtension.class)
class GitHubPullRequestParticipantFilterTest {
  @Mock
  private GHUser ghUser;
  @Mock
  private GHPullRequest pullRequest;
  @Mock
  private Participant participant;
  @InjectMocks
  private GitHubPullRequestParticipantFilter unitUnderTest;

  @BeforeEach
  void setUp() throws Exception {
    when(pullRequest.getUser()).thenReturn(ghUser);
  }

  @Test
  void test_shouldReturnTrueIfTheSame() {
    final String login = RandomStringUtils.randomAlphabetic(20);
    when(ghUser.getLogin()).thenReturn(login);
    when(participant.getLogin()).thenReturn(login);

    final boolean result = unitUnderTest.test(pullRequest, participant);

    assertTrue(result);
  }

  @Test
  void test_shouldReturnFalseIfNotTheSame() {
    when(ghUser.getLogin()).thenReturn(RandomStringUtils.randomAlphabetic(20));
    when(participant.getLogin()).thenReturn(RandomStringUtils.randomAlphabetic(20));

    final boolean result = unitUnderTest.test(pullRequest, participant);

    assertFalse(result);
  }

  @Test
  void test_shouldThrowAnAppropriateException() throws Exception {
    when(pullRequest.getUser()).thenThrow(IOException.class);

    assertThrows(RuntimeException.class, () -> {
      unitUnderTest.test(pullRequest, participant);
    });
  }
}