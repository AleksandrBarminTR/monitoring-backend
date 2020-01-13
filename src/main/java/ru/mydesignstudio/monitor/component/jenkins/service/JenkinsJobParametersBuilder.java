package ru.mydesignstudio.monitor.component.jenkins.service;

import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;

@Component
public class JenkinsJobParametersBuilder {
  public MultiValueMap<String, String> build(PullRequest pullRequest) {
    Objects.requireNonNull(pullRequest, "Pull request should not be null");

    final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("HEAD_HASH", pullRequest.getHeadHash());
    return params;
  }
}
