package ru.mydesignstudio.monitor.component.jenkins.service.job;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJobStatus;
import ru.mydesignstudio.monitor.component.jenkins.service.JenkinsService;

@Component
@RequiredArgsConstructor
public class NotCompletedJobsProvider {
  private final JenkinsService jenkinsService;

  public List<JenkinsJob> provide() {
    final List<JenkinsJob> jobs = new ArrayList<>();
    jobs.addAll(jenkinsService.findJobsByStatus(JenkinsJobStatus.NOT_STARTED));
    jobs.addAll(jenkinsService.findJobsByStatus(JenkinsJobStatus.IN_PROGRESS));
    return jobs;
  }
}
