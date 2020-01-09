package ru.mydesignstudio.monitor.component.pull.request.service.github;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;
import ru.mydesignstudio.monitor.component.pull.request.model.GitHubStatus;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;
import ru.mydesignstudio.monitor.component.pull.request.service.repository.RepositoryService;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubServiceImpl implements GitHubService {
  private final GitHubRepositoryProvider repositoryProvider;
  private final GitHubPullRequestProvider pullRequestProvider;
  private final GitHubPullRequestFilter<Participant> participantFilter;
  private final GitHubIssueStatusConverter statusConverter;
  private final RepositoryService repositoryService;
  private final PullRequestFactory pullRequestFactory;

  @Override
  public List<PullRequest> findAllOpenedPullRequests(Participant participant) {
    return findPullRequests(participant, Collections.singleton(GitHubStatus.OPEN));
  }

  @Override
  public List<PullRequest> findAllClosedPullRequests(Participant participant) {
    return findPullRequests(participant, Collections.singleton(GitHubStatus.CLOSED));
  }

  @Override
  public List<PullRequest> findAllPullRequests(Participant participant) {
    final Set<GitHubStatus> statutes = new HashSet<>();
    statutes.add(GitHubStatus.OPEN);
    statutes.add(GitHubStatus.CLOSED);
    return findPullRequests(participant, statutes);
  }

  @Override
  public List<PullRequest> findPullRequests(Participant participant, Set<GitHubStatus> statuses) {
    Objects.requireNonNull(participant, "Participant shouldn't be null");
    Objects.requireNonNull(statuses, "Status shouldn't be null");

    return repositoryService.findAll().parallelStream()
        .map(repositoryProvider::provide)
        .flatMap(repository -> pullRequestProvider.provide(repository, statusConverter.convert(statuses)).stream())
        .filter(pullRequest -> participantFilter.test(pullRequest, participant))
        .map(pullRequestFactory::create)
        .collect(Collectors.toList());
  }
}
