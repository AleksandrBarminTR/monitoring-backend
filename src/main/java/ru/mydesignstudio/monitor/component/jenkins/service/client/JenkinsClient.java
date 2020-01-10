package ru.mydesignstudio.monitor.component.jenkins.service.client;

import java.util.List;
import java.util.Map;

public interface JenkinsClient {
//  private final String host;
//  private final String username;
//  private final String password;

  Integer build(String jobFolders, String jobName, Map<String, List<String>> params);

  BuildInfo buildInfo(String jobFolders, String jobName, Integer buildNumber);
}
