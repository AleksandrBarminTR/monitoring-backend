package ru.mydesignstudio.monitor.component.jenkins.service.client;

import lombok.Data;
import lombok.NonNull;

@Data
public class BuildInfo {
  private @NonNull String result;
}
