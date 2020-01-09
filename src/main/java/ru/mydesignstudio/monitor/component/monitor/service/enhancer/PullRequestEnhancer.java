package ru.mydesignstudio.monitor.component.monitor.service.enhancer;

import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorResponse;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;

@Component
public class PullRequestEnhancer implements MonitorResponseEnhancer {

  @Override
  public void enhance(MonitorResponse.MonitorResponseBuilder response, PullRequest request) {
    response
        .githubLink(request.getUrl().toExternalForm())
        .githubStatus(request.getStatus())
        .githubTitle(request.getTitle());
  }
}
