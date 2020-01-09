package ru.mydesignstudio.monitor.component.pull.request.model;

import java.net.URL;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;

@Data
public class Repository {
  private @NonNull String repositoryName;
  private @NonNull URL repositoryUrl;
  private @NonNull URL jobLink;
}
