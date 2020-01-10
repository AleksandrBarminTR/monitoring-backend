package ru.mydesignstudio.monitor.component.jenkins.service;

import com.cdancy.jenkins.rest.domain.common.IntegerResponse;
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

  public JenkinsJob create(PullRequest pullRequest, IntegerResponse jenkinsResponse) {
    Objects.requireNonNull(pullRequest, "Pull request should not be null");
    Objects.requireNonNull(jenkinsResponse, "Jenkins response should not be null");

    return JenkinsJob.builder()
        .created(LocalDateTime.now())
        .status(JenkinsJobStatus.NOT_STARTED)
        .pullRequest(pullRequest.getUrl())
        .jobName(jobNameExtractor.extract(pullRequest.getRepository()))
        .jobFolder(jobFolderPathExtractor.extract(pullRequest.getRepository()))
        .buildNumber(jenkinsResponse.value())
        .headHash(pullRequest.getHeadHash())
        .build();
  }
}
