package ru.mydesignstudio.monitor.component.monitor.service.enhancer;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.service.JenkinsService;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorResponse.MonitorResponseBuilder;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;

@Component
@RequiredArgsConstructor
public class JenkinsEnhancer implements MonitorResponseEnhancer {
  private final JenkinsService jenkinsService;

  @Override
  public void enhance(MonitorResponseBuilder builder, PullRequest request) {
    final Optional<JenkinsJob> jenkinsJob = jenkinsService
        .findOneByHeadSha(request.getHeadHash());

    builder.buildResolved(jenkinsJob.isPresent());
    if (jenkinsJob.isPresent()) {
      final JenkinsJob job = jenkinsJob.get();

      if (job.getBuildUrl() != null) {
        builder.buildLink(job.getBuildUrl().toExternalForm());
      }
      builder.buildStatus(job.getStatus());
    }
  }
}
