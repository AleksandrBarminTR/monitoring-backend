package ru.mydesignstudio.monitor.component.pull.request.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindResponse {
  private String githubLink;
  private GitHubStatus githubStatus;
  private boolean jiraResolved;
  private JiraStatus jiraStatus;
  private String jiraLink;
  private boolean buildResolved;
  private String buildLink;
  private BuildStatus buildStatus;
}
