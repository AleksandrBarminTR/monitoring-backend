package ru.mydesignstudio.monitor.component.monitor.service;

import java.util.List;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorRequest;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorResponse;

public interface MonitorService {
  List<MonitorResponse> process(MonitorRequest request);
}
