package ru.mydesignstudio.monitor.component.monitor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJobStatus;
import ru.mydesignstudio.monitor.component.jira.model.JiraTicketStatus;
import ru.mydesignstudio.monitor.component.pull.request.model.GitHubStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonitorResponse {
  private String githubLink;
  private GitHubStatus githubStatus;
  private String githubTitle;
  private boolean jiraResolved;
  private JiraTicketStatus jiraStatus;
  private String jiraLink;
  private String jiraTitle;
  private boolean buildResolved;
  private String buildLink;
  private JenkinsJobStatus buildStatus;
}
