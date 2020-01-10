package ru.mydesignstudio.monitor.component.jenkins.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;

@Component
public class JenkinsJobParametersBuilder {
  public Map<String, List<String>> build(PullRequest pullRequest) {
    Objects.requireNonNull(pullRequest, "Pull request should not be null");

    final Map<String, List<String>> params = new HashMap<>();
    params.put("buildHead", Collections.singletonList(pullRequest.getHeadHash()));
    return params;
  }
}
