package ru.mydesignstudio.monitor.component.jenkins.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJobStatus;
import ru.mydesignstudio.monitor.component.jenkins.service.client.BuildInfo;
import ru.mydesignstudio.monitor.component.jenkins.service.client.JenkinsClient;

@Component
@RequiredArgsConstructor
public class JenkinsJobStatusCheckerImpl implements JenkinsJobStatusChecker {
  private final JenkinsClient jenkinsClient;

  @Override
  public JenkinsJobStatus check(JenkinsJob jenkinsJob) {
    Objects.requireNonNull(jenkinsJob, "Jenkins job should be provided");

    final BuildInfo info;
    if (isStarted(jenkinsJob)) {
      info = jenkinsClient
          .buildInfo(jenkinsJob.getJobFolder(), jenkinsJob.getJobName(), jenkinsJob.getBuildNumber());
    } else {
      info = jenkinsClient.buildInfo(jenkinsJob.getQueueNumber());
    }

    if (StringUtils.equalsIgnoreCase("IN_PROGRESS", info.getResult())) {
      // TODO, get rid of side effect
      jenkinsJob.setBuildNumber(info.getBuildNumber());
      return JenkinsJobStatus.IN_PROGRESS;
    } else if (StringUtils.equalsIgnoreCase("COMPLETED_UNKNOWN_RESULTS", info.getResult())) {
      return JenkinsJobStatus.MISSED;
    } else if (StringUtils.equalsIgnoreCase("SUCCESS", info.getResult())) {
      jenkinsJob.setBuildUrl(info.getUrl());
      return JenkinsJobStatus.SUCCESS;
    } else if (StringUtils.equalsIgnoreCase("FAILURE", info.getResult())) {
      jenkinsJob.setBuildUrl(info.getUrl());
      return JenkinsJobStatus.FAILED;
    } else if (StringUtils.isEmpty(info.getResult())) {
      return JenkinsJobStatus.IN_PROGRESS;
    } else {
      throw new RuntimeException(String.format(
          "Unsupported result %s",
          info.getResult()
      ));
    }
  }

  private boolean isStarted(JenkinsJob jenkinsJob) {
    return jenkinsJob.getBuildNumber() != 0;
  }
}
