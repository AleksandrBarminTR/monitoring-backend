package ru.mydesignstudio.monitor.component.pull.request.service.github;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kohsuke.github.GHCommitPointer;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHUser;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;
import ru.mydesignstudio.monitor.component.participant.service.ParticipantService;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;

@ExtendWith(MockitoExtension.class)
class PullRequestFactoryTest {
  private final String pullRequestUrl = "http://" + RandomStringUtils.randomAlphabetic(10);
  private final String pullRequestTitle = RandomStringUtils.randomAlphabetic(10);
  private final String pullRequestHeadSha = RandomStringUtils.randomAlphabetic(10);
  private final String participantLogin = RandomStringUtils.randomAlphabetic(10);

  @Mock
  private GHCommitPointer headCommitPointer;
  @Mock
  private Participant participant;
  @Mock
  private GHUser author;
  @Mock
  private GHPullRequest pullRequest;
  @Mock
  private ParticipantService participantService;
  @InjectMocks
  private PullRequestFactory unitUnderTest;

  @BeforeEach
  void setUp() throws Exception {
    when(pullRequest.getHtmlUrl()).thenReturn(new URL(pullRequestUrl));
    when(pullRequest.getUser()).thenReturn(author);
    when(pullRequest.getTitle()).thenReturn(pullRequestTitle);
    when(pullRequest.getHead()).thenReturn(headCommitPointer);
    when(headCommitPointer.getSha()).thenReturn(pullRequestHeadSha);

    when(author.getLogin()).thenReturn(participantLogin);

    when(participantService.findByLogin(participantLogin)).thenReturn(Optional.of(participant));
  }

  @Test
  void create_shouldSaveHeadHash() {
    final PullRequest request = unitUnderTest.create(this.pullRequest);

    assertAll(
        () -> assertNotNull(request.getHeadHash()),
        () -> assertEquals(pullRequestHeadSha, request.getHeadHash())
    );
  }
}
