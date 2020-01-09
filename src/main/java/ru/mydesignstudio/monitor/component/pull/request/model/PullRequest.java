package ru.mydesignstudio.monitor.component.pull.request.model;

import java.net.URL;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;

@Data
@Builder
@AllArgsConstructor
public class PullRequest {
  private @NonNull URL url;
  private @NonNull String title;
  private @NonNull GitHubStatus status;
  private @NonNull Participant createdBy;
  private @NonNull String headHash;
  private @NonNull List<Participant> reviewers;
}
