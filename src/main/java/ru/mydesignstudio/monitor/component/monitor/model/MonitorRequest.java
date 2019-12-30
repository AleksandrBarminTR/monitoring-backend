package ru.mydesignstudio.monitor.component.monitor.model;

import java.util.EnumSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.mydesignstudio.monitor.component.pull.request.model.GitHubStatus;

@Data
@NoArgsConstructor
public class MonitorRequest {
  private @NonNull String participantName;
  private @NonNull Set<GitHubStatus> statuses = EnumSet.noneOf(GitHubStatus.class);
}
