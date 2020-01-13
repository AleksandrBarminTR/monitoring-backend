package ru.mydesignstudio.monitor.component.pull.request.service.github;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;
import ru.mydesignstudio.monitor.component.participant.service.ParticipantService;
import ru.mydesignstudio.monitor.component.pull.request.model.GitHubStatus;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;
import ru.mydesignstudio.monitor.component.pull.request.service.repository.RepositoryService;

@Component
@RequiredArgsConstructor
public class PullRequestFactory {
  private final ParticipantService participantService;
  private final RepositoryService repositoryService;

  public PullRequest create(GHPullRequest request) {
    return PullRequest.builder()
        .url(request.getHtmlUrl())
        .createdBy(findAuthor(request))
        .reviewers(findReviewers(request))
        .status(findStatus(request))
        .title(request.getTitle())
        .headHash(request.getHead().getSha())
        .repository(findRepository(request))
        .pullRequestId(request.getNumber())
        .build();
  }

  private Repository findRepository(GHPullRequest request) {
    return repositoryService.findByUrl(request.getRepository().getHtmlUrl())
        .orElseThrow(() -> new RuntimeException(String.format(
            "Can't find repository with URL %s",
            request.getRepository().getHtmlUrl()
        )));
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
