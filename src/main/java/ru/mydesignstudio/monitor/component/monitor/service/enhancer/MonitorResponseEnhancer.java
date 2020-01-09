package ru.mydesignstudio.monitor.component.monitor.service.enhancer;

import ru.mydesignstudio.monitor.component.monitor.model.MonitorResponse;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;

public interface MonitorResponseEnhancer {
  void enhance(MonitorResponse.MonitorResponseBuilder builder, PullRequest request);
}
