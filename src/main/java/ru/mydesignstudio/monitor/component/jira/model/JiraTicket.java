package ru.mydesignstudio.monitor.component.jira.model;

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
  private @NonNull String link;
  private @NonNull JiraTicketStatus status;
}
