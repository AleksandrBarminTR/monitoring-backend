package ru.mydesignstudio.monitor.component.jenkins.service.job;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.service.JenkinsService;

@Component
@RequiredArgsConstructor
public class NotStartedJobsProvider {
  private final JenkinsService jenkinsService;

  public List<JenkinsJob> provide() {
    return jenkinsService.findUnstartedJobs();
  }
}
