package ru.mydesignstudio.monitor.component.jenkins.service.client;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class JenkinsClientRestImpl implements JenkinsClient {

  private final String host;
  private final String username;
  private final String password;
  private final RestTemplate restTemplate;

  @Override
  public Integer build(String jobFolders, String jobName, Map<String, List<String>> params) {
    throw new UnsupportedOperationException();
  }

  @Override
  public BuildInfo buildInfo(String jobFolders, String jobName, Integer buildNumber) {
    throw new UnsupportedOperationException();
  }
}
