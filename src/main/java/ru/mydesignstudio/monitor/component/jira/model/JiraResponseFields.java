package ru.mydesignstudio.monitor.component.jira.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class JiraResponseFields {
  private @NonNull String summary;
  private @NonNull JiraResponseStatus status;
}
