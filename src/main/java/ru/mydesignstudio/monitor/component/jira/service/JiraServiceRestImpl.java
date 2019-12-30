package ru.mydesignstudio.monitor.component.jira.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.mydesignstudio.monitor.component.jira.model.JiraResponse;
import ru.mydesignstudio.monitor.component.jira.model.JiraTicket;

@Slf4j
@Service
@RequiredArgsConstructor
public class JiraServiceRestImpl implements JiraService {
  private final RestTemplate restTemplate;
  private final JiraTicketFactory ticketFactory;
  @Value("${jira.api.ticket.pattern}")
  private String jiraTicketPattern;

  @Override
  public Optional<JiraTicket> findTicket(String ticketNumber) {
    try {
      log.debug("Loading information about ticket {}", ticketNumber);
      final JiraResponse jiraResponse = restTemplate.getForObject(ticketUrl(ticketNumber), JiraResponse.class);
      return Optional.of(ticketFactory.create(jiraResponse));
    } catch (RestClientException e) {
      log.error("Can't load ticket {}", ticketNumber, e);
      return Optional.empty();
    }
  }

  private URI ticketUrl(String ticketNumber) {
    try {
      log.debug("Building URL for ticket {}", ticketNumber);
      return new URI(String.format(jiraTicketPattern, ticketNumber));
    } catch (URISyntaxException e) {
      log.error("Can't build URL for ticket {}", ticketNumber);
      throw new RuntimeException("Can't build URL for ticket " + ticketNumber, e);
    }
  }
}
