package ru.mydesignstudio.monitor.component.participant.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ParticipantModel {
  private @NonNull String login;
  private @NonNull String name;
}
