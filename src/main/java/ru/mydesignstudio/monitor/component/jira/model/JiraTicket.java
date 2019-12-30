package ru.mydesignstudio.monitor.component.jira.model;

import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class JiraTicket {
  private @NonNull URL url;
  private @NonNull String ticket;
  private @NonNull JiraTicketStatus status;
}
