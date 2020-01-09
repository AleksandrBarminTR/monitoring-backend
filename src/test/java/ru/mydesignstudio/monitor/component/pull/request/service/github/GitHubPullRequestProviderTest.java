package ru.mydesignstudio.monitor.component.pull.request.service.github;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GitHubPullRequestProviderTest {
  @Mock
  private GHRepository repository;
  @Mock
  private GHPullRequest pullRequest;
  @InjectMocks
  private GitHubPullRequestProvider unitUnderTest;

  @ParameterizedTest
  @EnumSource(GHIssueState.class)
  void provide_providesNormally(GHIssueState state) throws Exception {
    when(repository.getPullRequests(state)).thenReturn(Collections.singletonList(pullRequest));

    final List<GHPullRequest> pullRequests = unitUnderTest.provide(repository, state);

    assertAll(
        () -> assertNotNull(pullRequests),
        () -> assertFalse(pullRequests.isEmpty())
    );
  }

  @ParameterizedTest
  @EnumSource(GHIssueState.class)
  void provide_throwsAppropriateException(GHIssueState state) throws Exception {
    when(repository.getPullRequests(any(GHIssueState.class))).thenThrow(IOException.class);

    assertThrows(RuntimeException.class, () -> {
      unitUnderTest.provide(repository, state);
    });
  }
}