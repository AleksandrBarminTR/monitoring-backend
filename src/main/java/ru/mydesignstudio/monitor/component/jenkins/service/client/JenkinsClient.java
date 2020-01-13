package ru.mydesignstudio.monitor.component.jenkins.service.client;

import org.springframework.util.MultiValueMap;

public interface JenkinsClient {
  Integer build(String jobFolders, String jobName, MultiValueMap<String, String> params);

  BuildInfo buildInfo(String jobFolders, String jobName, Integer buildNumber);

  BuildInfo buildInfo(int queueNumber);
}
