package ru.mydesignstudio.monitor.component.jenkins.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.service.job.NewPullRequestsChecker;
import ru.mydesignstudio.monitor.component.jenkins.service.job.OpenedPullRequestsProvider;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;
import ru.mydesignstudio.monitor.component.pull.request.service.github.GitHubNotificator;

@ExtendWith(MockitoExtension.class)
class NewPullRequestsCheckerTest {
  @Mock
  private JenkinsJobStarter jobStarter;
  @Mock
  private JenkinsService jenkinsService;
  @Mock
  private JenkinsJob jenkinsJob;
  @Mock
  private PullRequest pullRequest;
  @Mock
  private OpenedPullRequestsProvider openedPullRequestsProvider;
  @Mock
  private GitHubNotificator gitHubNotificator;
  @InjectMocks
  private NewPullRequestsChecker unitUnderTest;

  @Test
  void check_everythingStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void execute_shouldCheckForNewMergeRequests() {
    unitUnderTest.execute();

    verify(openedPullRequestsProvider, times(1)).provide();
  }

  @Test
  void execute_shouldCheckIfJobForPullRequestExists() {
    when(openedPullRequestsProvider.provide()).thenReturn(Collections.singletonList(pullRequest));
    when(pullRequest.getHeadHash()).thenReturn(RandomStringUtils.randomAlphabetic(10));

    unitUnderTest.execute();

    verify(jenkinsService, times(1)).findOneByHeadSha(anyString());
  }

  @Test
  void execute_forNewPullRequestsJobsShouldBeCreated() {
    final String headHash = RandomStringUtils.randomAlphabetic(10);
    when(openedPullRequestsProvider.provide()).thenReturn(Collections.singletonList(pullRequest));
    when(pullRequest.getHeadHash()).thenReturn(headHash);
    when(jenkinsService.findOneByHeadSha(headHash)).thenReturn(Optional.empty());
    when(jobStarter.start(any(PullRequest.class))).thenReturn(jenkinsJob);

    unitUnderTest.execute();

    verify(jobStarter, times(1)).start(pullRequest);
    verify(jenkinsService, times(1)).save(any(JenkinsJob.class));
  }
}
