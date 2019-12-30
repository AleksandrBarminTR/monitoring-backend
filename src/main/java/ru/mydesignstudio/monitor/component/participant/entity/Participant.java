package ru.mydesignstudio.monitor.component.participant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Participant {
  private @NonNull String name;
  private @NonNull String login;
}
