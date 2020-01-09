package ru.mydesignstudio.monitor.component.jenkins.service.job;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.participant.service.ParticipantService;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;
import ru.mydesignstudio.monitor.component.pull.request.service.github.GitHubService;

@Component
@RequiredArgsConstructor
public class OpenedPullRequestsProvider {
  private final GitHubService gitHubService;
  private final ParticipantService participantService;

  public List<PullRequest> provide() {
    return participantService.findAll()
        .stream()
        .map(participant -> gitHubService.findAllOpenedPullRequests(participant))
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }
}
