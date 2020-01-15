package ru.mydesignstudio.monitor.component.jira.service.ticket;

import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.jira.model.JiraTicketStatus;

@Component
public class JiraTicketStatusFactory {
  public JiraTicketStatus create(String statusName) {
    return JiraTicketStatus.byJiraStatus(statusName)
        .orElse(JiraTicketStatus.UNKNOWN);
  }
}
