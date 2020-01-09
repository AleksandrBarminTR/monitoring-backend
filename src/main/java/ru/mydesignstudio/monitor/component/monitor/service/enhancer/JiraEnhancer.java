package ru.mydesignstudio.monitor.component.monitor.service.enhancer;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.jira.model.JiraTicket;
import ru.mydesignstudio.monitor.component.jira.service.JiraService;
import ru.mydesignstudio.monitor.component.jira.service.number.extractor.TicketNumberExtractor;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorResponse.MonitorResponseBuilder;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;

@Component
@RequiredArgsConstructor
public class JiraEnhancer implements MonitorResponseEnhancer {
  private final TicketNumberExtractor ticketNumberExtractor;
  private final JiraService jiraService;

  @Override
  public void enhance(MonitorResponseBuilder builder, PullRequest request) {
    final Optional<JiraTicket> jiraTicket = ticketNumberExtractor
        .extract(request.getTitle())
        .flatMap(number -> jiraService.findTicket(number));

    builder.jiraResolved(jiraTicket.isPresent());
    if (jiraTicket.isPresent()) {
      final JiraTicket jira = jiraTicket.get();
      builder.jiraLink(jira.getLink().toExternalForm());
      builder.jiraStatus(jira.getStatus());
      builder.jiraTitle(jira.getDescription());
    }
  }
}
