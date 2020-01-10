package ru.mydesignstudio.monitor.component.jenkins.service;

import com.cdancy.jenkins.rest.domain.job.BuildInfo;
import com.cdancy.jenkins.rest.features.JobsApi;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJobStatus;

@Component
@RequiredArgsConstructor
public class JenkinsJobStatusCheckerImpl implements JenkinsJobStatusChecker {
  private final JobsApi jobsApi;

  @Override
  public JenkinsJobStatus check(JenkinsJob jenkinsJob) {
    Objects.requireNonNull(jenkinsJob, "Jenkins job should be provided");

    final BuildInfo info = jobsApi
        .buildInfo(jenkinsJob.getJobFolder(), jenkinsJob.getJobName(), jenkinsJob.getBuildNumber());

    if (StringUtils.equalsIgnoreCase("SUCCESS", info.result())) {
      return JenkinsJobStatus.SUCCESS;
    } else if (StringUtils.equalsIgnoreCase("FAILURE", info.result())) {
      return JenkinsJobStatus.FAILED;
    } else if (StringUtils.isEmpty(info.result())) {
      return JenkinsJobStatus.IN_PROGRESS;
    } else {
      throw new RuntimeException(String.format(
          "Unsupported result %s",
          info.result()
      ));
    }
  }
}
