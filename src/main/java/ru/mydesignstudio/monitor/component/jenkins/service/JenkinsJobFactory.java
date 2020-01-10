package ru.mydesignstudio.monitor.component.jenkins.service;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJobStatus;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;

@Component
@RequiredArgsConstructor
public class JenkinsJobFactory {
  private final JenkinsJobNameExtractor jobNameExtractor;
  private final JenkinsJobFolderPathExtractor jobFolderPathExtractor;

  public JenkinsJob create(PullRequest pullRequest, Integer buildNumber) {
    Objects.requireNonNull(pullRequest, "Pull request should not be null");
    Objects.requireNonNull(buildNumber, "Jenkins response should not be null");

    return JenkinsJob.builder()
        .created(LocalDateTime.now())
        .status(JenkinsJobStatus.NOT_STARTED)
        .pullRequest(pullRequest.getUrl())
        .jobName(jobNameExtractor.extract(pullRequest.getRepository()))
        .jobFolder(jobFolderPathExtractor.extract(pullRequest.getRepository()))
        .buildNumber(buildNumber)
        .headHash(pullRequest.getHeadHash())
        .build();
  }
}
