package ru.mydesignstudio.monitor.component.jenkins.service.job;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJobStatus;
import ru.mydesignstudio.monitor.component.jenkins.service.JenkinsJobStatusChecker;
import ru.mydesignstudio.monitor.component.jenkins.service.JenkinsService;

@Component
@RequiredArgsConstructor
public class NotCompletedJobsChecker implements ScheduledJob {
  private final NotCompletedJobsProvider jobsProvider;
  private final JenkinsJobStatusChecker statusChecker;
  private final JenkinsService jenkinsService;

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
