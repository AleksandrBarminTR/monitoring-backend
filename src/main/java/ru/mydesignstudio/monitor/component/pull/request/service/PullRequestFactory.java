package ru.mydesignstudio.monitor.component.pull.request.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.pull.request.model.GitHubStatus;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;
import ru.mydesignstudio.monitor.component.participant.service.ParticipantService;

@Component
@RequiredArgsConstructor
public class PullRequestFactory {
  private final ParticipantService participantService;

  public PullRequest create(GHPullRequest request) {
    return PullRequest.builder()
        .url(request.getUrl())
        .createdBy(findAuthor(request))
        .reviewers(findReviewers(request))
        .status(findStatus(request))
        .title(request.getTitle())
        .build();
  }

  private GitHubStatus findStatus(GHPullRequest request) {
    return request.getState() == GHIssueState.CLOSED ?
        GitHubStatus.CLOSED :
        GitHubStatus.OPEN;
  }

  private List<Participant> findReviewers(GHPullRequest request) {
    return request.getAssignees().stream()
        .map(user -> findParticipant(user.getLogin()))
        .collect(Collectors.toList());
  }

  private Participant findParticipant(String login) {
    return participantService.findByLogin(login)
        .orElseThrow(() -> new RuntimeException("Can't find participant with login " + login));
  }

  @SneakyThrows
  private Participant findAuthor(GHPullRequest request) {
    return findParticipant(request.getUser().getLogin());
  }
}
