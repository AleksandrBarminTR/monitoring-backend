package ru.mydesignstudio.monitor.component.jira.model;

import java.util.Arrays;
import java.util.Optional;

public enum JiraTicketStatus {
  OPEN("Open"),
  IN_PROGRESS("In Progress"),
  CODE_REVIEW("Code Review by the TR dev lead"),
  CODE_REVIEWED("Development Completed"),
  DEPLOYED_READY_TO_TEST("Deployed Ready to Test"),
  CLOSED("Closed"),
  UNKNOWN("Unknown");

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
