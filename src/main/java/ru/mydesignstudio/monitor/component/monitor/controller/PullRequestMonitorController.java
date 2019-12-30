package ru.mydesignstudio.monitor.component.monitor.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorRequest;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorResponse;
import ru.mydesignstudio.monitor.component.monitor.service.MonitorService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pull-requests")
public class PullRequestMonitorController {
  private final MonitorService monitorService;

  @PostMapping
  public List<MonitorResponse> getInfo(@RequestBody MonitorRequest request) {
    return monitorService.process(request);
  }
}
