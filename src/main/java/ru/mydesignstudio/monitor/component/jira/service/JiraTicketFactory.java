package ru.mydesignstudio.monitor.component.jira.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.jira.model.JiraResponse;
import ru.mydesignstudio.monitor.component.jira.model.JiraTicket;

@Component
@RequiredArgsConstructor
public class JiraTicketFactory {
  private final JiraTicketStatusFactory statusFactory;

  public JiraTicket create(JiraResponse response) {
    return JiraTicket.builder()
        .ticket(response.getKey())
        .link(response.getSelf())
        .description(response.getFields().getSummary())
        .status(statusFactory.create(response.getFields().getStatus().getName()))
        .build();
  }
}
