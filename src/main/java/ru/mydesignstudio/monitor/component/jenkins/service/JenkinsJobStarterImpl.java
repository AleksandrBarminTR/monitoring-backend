package ru.mydesignstudio.monitor.component.jenkins.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.service.client.JenkinsClient;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;

@Component
@RequiredArgsConstructor
public class JenkinsJobStarterImpl implements JenkinsJobStarter {
  private final JenkinsJobParametersBuilder parametersBuilder;
  private final JenkinsJobNameExtractor nameExtractor;
  private final JenkinsJobFolderPathExtractor folderPathExtractor;
  private final JenkinsClient jenkinsClient;
  private final JenkinsJobFactory jobFactory;

  @Override
  public JenkinsJob start(PullRequest pullRequest) {
    Objects.requireNonNull(pullRequest, "Pull request should not be null");

    final Map<String, List<String>> parameters = parametersBuilder.build(pullRequest);
    final String jobName = nameExtractor.extract(pullRequest.getRepository());
    final String jobFolder = folderPathExtractor.extract(pullRequest.getRepository());

    final Integer buildNumber = jenkinsClient
        .build(jobFolder, jobName, parameters);

    return jobFactory.create(pullRequest, buildNumber);
  }
}
