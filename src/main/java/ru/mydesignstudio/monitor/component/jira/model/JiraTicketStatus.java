package ru.mydesignstudio.monitor.component.jira.model;

import java.util.Arrays;
import java.util.Optional;

public enum JiraTicketStatus {
  OPEN("Open"),
  IN_PROGRESS("In Progress"),
  CODE_REVIEW("code_review"),
  CODE_REVIEWED("Development Completed"),
  CLOSED("Closed");

  private final String jiraEquivalent;

  JiraTicketStatus(String jiraEquivalent) {
    this.jiraEquivalent = jiraEquivalent;
  }

  public static Optional<JiraTicketStatus> byJiraStatus(String jiraStatus) {
    return Arrays.stream(values())
        .filter(status -> jiraStatus.equalsIgnoreCase(status.jiraEquivalent))
        .findFirst();
  }
}
