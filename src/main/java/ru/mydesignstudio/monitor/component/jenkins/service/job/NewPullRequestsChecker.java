package ru.mydesignstudio.monitor.component.jenkins.service.job;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.jenkins.service.JenkinsJobStarter;
import ru.mydesignstudio.monitor.component.jenkins.service.JenkinsService;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;

@Component
@RequiredArgsConstructor
public class NewPullRequestsChecker implements ScheduledJob {
  private final OpenedPullRequestsProvider pullRequestsProvider;
  private final JenkinsService jenkinsService;
  private final JenkinsJobStarter jenkinsJobStarter;

  @Override
  public void execute() {
    pullRequestsProvider.provide()
        .parallelStream()
        .filter(this::isJobExists)
        .map(jenkinsJobStarter::start)
        .forEach(jenkinsService::save);
  }

  private boolean isJobExists(PullRequest pullRequest) {
    return !jenkinsService.findOneByHeadSha(pullRequest.getHeadHash())
        .isPresent();
  }
}
