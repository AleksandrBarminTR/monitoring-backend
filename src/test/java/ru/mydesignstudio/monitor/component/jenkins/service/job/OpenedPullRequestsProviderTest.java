package ru.mydesignstudio.monitor.component.jenkins.service.job;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;
import ru.mydesignstudio.monitor.component.participant.service.ParticipantService;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;
import ru.mydesignstudio.monitor.component.pull.request.service.github.GitHubService;
import ru.mydesignstudio.monitor.component.pull.request.service.repository.RepositoryService;

@ExtendWith(MockitoExtension.class)
class OpenedPullRequestsProviderTest {
  @Mock
  private Participant participant;
  @Mock
  private PullRequest pullRequest;

  @Mock
  private ParticipantService participantService;
  @Mock
  private RepositoryService repositoryService;
  @Mock
  private GitHubService gitHubService;
  @Mock
  private Repository repository;
  @InjectMocks
  private OpenedPullRequestsProvider unitUnderTests;

  @Test
  void check_everythingStarts() {
    assertNotNull(unitUnderTests);
  }

  @Test
  void provide_shouldReturnEmptyListIfNothingExists() {
    when(repositoryService.findAll()).thenReturn(Collections.singletonList(repository));
    when(repository.getRepositoryName()).thenReturn("app-editorial-new");

    final List<PullRequest> requests = unitUnderTests.provide();

    assertAll(
        () -> assertNotNull(requests),
        () -> assertTrue(requests.isEmpty())
    );
  }

  @Test
  void provide_shouldReturnFilledListIfRequestsExist() {
    when(repositoryService.findAll()).thenReturn(Collections.singletonList(repository));
    when(repository.getRepositoryName()).thenReturn("app-editorial-new");
    when(gitHubService.findAllOpenedPullRequests(repository)).thenReturn(Collections.singletonList(pullRequest));

    final List<PullRequest> requests = unitUnderTests.provide();

    assertAll(
        () -> assertNotNull(requests),
        () -> assertFalse(requests.isEmpty())
    );
  }
}
