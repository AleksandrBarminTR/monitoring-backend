package ru.mydesignstudio.monitor.component.pull.request.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;
import ru.mydesignstudio.monitor.component.pull.request.service.github.GitHubPullRequestProvider;
import ru.mydesignstudio.monitor.component.pull.request.service.github.GitHubRepositoryProvider;
import ru.mydesignstudio.monitor.component.pull.request.service.github.GitHubServiceImpl;
import ru.mydesignstudio.monitor.component.pull.request.service.github.PullRequestFactory;
import ru.mydesignstudio.monitor.component.pull.request.service.repository.RepositoryService;

@ExtendWith(MockitoExtension.class)
class GitHubServiceImplTest {
  private static final String USER_LOGIN = RandomStringUtils.randomAlphabetic(20);

  @Mock
  private PullRequestFactory pullRequestFactory;
  @Mock
  private RepositoryService repositoryService;
  @Mock
  private Participant participant;
  @Mock
  private Repository repository;
  @Mock
  private GHRepository ghRepository;
  @Mock
  private GHPullRequest ghPullRequest;
  @Mock
  private GHUser ghUser;
  @Mock
  private GitHubRepositoryProvider repositoryProvider;
  @Mock
  private GitHubPullRequestProvider pullRequestProvider;

  @InjectMocks
  private GitHubServiceImpl unitUnderTest;

  @Test
  void check_contextStarts() {
    assertAll(
        () -> assertNotNull(unitUnderTest)
    );
  }

  @Test
  void findAllOpenedPullRequests_shouldCheckInputArguments() {
    assertThrows(NullPointerException.class, () -> {
      unitUnderTest.findAllOpenedPullRequests((Participant) null);
    });
  }

  @Test
  @Disabled
  void findAllOpenedPullRequests_shouldReturnRequests() throws Exception {
    when(repositoryProvider.provide(any(Repository.class))).thenReturn(ghRepository);
    when(repositoryService.findAll()).thenReturn(Collections.singletonList(repository));
    when(ghRepository.getPullRequests(any(GHIssueState.class))).thenReturn(Collections.singletonList(ghPullRequest));
    when(ghPullRequest.getUser()).thenReturn(ghUser);
    when(ghUser.getLogin()).thenReturn(USER_LOGIN);
    when(participant.getLogin()).thenReturn(USER_LOGIN);

    final List<PullRequest> requests = unitUnderTest
        .findAllOpenedPullRequests(participant);

    assertAll(
        () -> assertNotNull(requests),
        () -> assertFalse(requests.isEmpty())
    );
  }
}
