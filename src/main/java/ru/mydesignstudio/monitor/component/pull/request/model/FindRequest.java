package ru.mydesignstudio.monitor.component.pull.request.model;

import java.util.EnumSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class FindRequest {
  private @NonNull String participantName;
  private @NonNull Set<GitHubStatus> statuses = EnumSet.noneOf(GitHubStatus.class);
}
