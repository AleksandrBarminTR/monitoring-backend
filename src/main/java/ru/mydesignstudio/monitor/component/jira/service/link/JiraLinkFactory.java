package ru.mydesignstudio.monitor.component.jira.service.link;

import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JiraLinkFactory {
  @Value("${jira.html.ticket.pattern}")
  private String jiraLinkTemplate;

  public URL create(String ticketNumber) {
    try {
      return new URL(String.format(jiraLinkTemplate, ticketNumber));
    } catch (MalformedURLException e) {
      throw new RuntimeException("Can't create URL for ticket " + ticketNumber, e);
    }
  }
}
