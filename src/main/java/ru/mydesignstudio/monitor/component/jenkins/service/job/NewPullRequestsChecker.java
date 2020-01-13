package ru.mydesignstudio.monitor.component.jenkins.service.job;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.service.JenkinsJobStarter;
import ru.mydesignstudio.monitor.component.jenkins.service.JenkinsService;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;
import ru.mydesignstudio.monitor.component.pull.request.service.github.GitHubNotificator;

@Component
@RequiredArgsConstructor
public class NewPullRequestsChecker implements ScheduledJob {

  private final OpenedPullRequestsProvider pullRequestsProvider;
  private final JenkinsService jenkinsService;
  private final JenkinsJobStarter jenkinsJobStarter;
  private final GitHubNotificator gitHubNotificator;

  @Override
  public void execute() {
    pullRequestsProvider.provide()
        .stream()
        .filter(this::isJobExists)
        .map(pullRequest -> {
          final JenkinsJob job = jenkinsJobStarter.start(pullRequest);
          sendNotification(pullRequest, job);
          return job;
        })
        .forEach(jenkinsService::save);
  }

  private boolean isJobExists(PullRequest pullRequest) {
    return !jenkinsService.findOneByHeadSha(pullRequest.getHeadHash())
        .isPresent();
  }

  // TODO, move to the appropriate place
  private void sendNotification(PullRequest pullRequest, JenkinsJob jenkinsJob) {
    gitHubNotificator.sendNotification(pullRequest,
        "Jenkins job has been created");
  }
}
