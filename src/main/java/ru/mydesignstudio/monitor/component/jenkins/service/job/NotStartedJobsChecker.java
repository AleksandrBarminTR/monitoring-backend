package ru.mydesignstudio.monitor.component.jenkins.service.job;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJobStatus;
import ru.mydesignstudio.monitor.component.jenkins.service.JenkinsJobStatusChecker;
import ru.mydesignstudio.monitor.component.jenkins.service.JenkinsService;
import ru.mydesignstudio.monitor.component.pull.request.service.github.GitHubNotificator;

@Component
@RequiredArgsConstructor
public class NotStartedJobsChecker implements ScheduledJob {
  private final NotStartedJobsProvider jobsProvider;
  private final JenkinsJobStatusChecker statusChecker;
  private final JenkinsService jenkinsService;
  private final GitHubNotificator notificator;

  @Override
  public void execute() {
    for (JenkinsJob jenkinsJob : jobsProvider.provide()) {
      final JenkinsJobStatus providedStatus = statusChecker.check(jenkinsJob);
      if (providedStatus != jenkinsJob.getStatus()) {
        jenkinsJob.setStatus(providedStatus);
        jenkinsService.save(jenkinsJob);
      }
    }
  }
}
