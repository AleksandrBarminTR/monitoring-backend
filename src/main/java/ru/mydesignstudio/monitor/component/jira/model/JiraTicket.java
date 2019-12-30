package ru.mydesignstudio.monitor.component.jira.model;

import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;

@Data
@Builder
@AllArgsConstructor
public class JiraTicket {
  private @NonNull String ticket;
  private @NonNull String description;
  private @NonNull URL link;
  private @NonNull JiraTicketStatus status;
}
