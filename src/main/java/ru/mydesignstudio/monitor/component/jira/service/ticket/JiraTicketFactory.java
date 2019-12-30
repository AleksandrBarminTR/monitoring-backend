package ru.mydesignstudio.monitor.component.jira.service.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.jira.model.JiraResponse;
import ru.mydesignstudio.monitor.component.jira.model.JiraTicket;
import ru.mydesignstudio.monitor.component.jira.service.link.JiraLinkFactory;

@Component
@RequiredArgsConstructor
public class JiraTicketFactory {
  private final JiraTicketStatusFactory statusFactory;
  private final JiraLinkFactory linkFactory;

  public JiraTicket create(JiraResponse response) {
    return JiraTicket.builder()
        .ticket(response.getKey())
        .link(linkFactory.create(response.getKey()))
        .description(response.getFields().getSummary())
        .status(statusFactory.create(response.getFields().getStatus().getName()))
        .build();
  }
}
