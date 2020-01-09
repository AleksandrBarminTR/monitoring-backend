package ru.mydesignstudio.monitor.component.pull.request.service.github;

import java.util.Set;
import org.kohsuke.github.GHIssueState;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.pull.request.model.GitHubStatus;

@Component
public class GitHubIssueStatusConverter {
  public GHIssueState convert(Set<GitHubStatus> statuses) {
    if (statuses.contains(GitHubStatus.OPEN) && statuses.contains(GitHubStatus.CLOSED)) {
      return GHIssueState.ALL;
    } else if (statuses.contains(GitHubStatus.OPEN)) {
      return GHIssueState.OPEN;
    } else if (statuses.isEmpty()) {
      return GHIssueState.ALL;
    } else {
      return GHIssueState.CLOSED;
    }
  }
}
