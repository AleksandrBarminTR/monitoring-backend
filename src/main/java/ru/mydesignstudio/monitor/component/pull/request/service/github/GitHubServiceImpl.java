package ru.mydesignstudio.monitor.component.pull.request.service.github;

import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHRepository;
import org.springframework.stereotype.Service;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;
import ru.mydesignstudio.monitor.component.pull.request.model.GitHubStatus;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;
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

    return repositoryService.findAll()
        .stream()
        .map(repositoryProvider::provide)
        .flatMap(repository -> pullRequestProvider.provide(repository, statusConverter.convert(statuses)).stream())
        .filter(pullRequest -> participantFilter.test(pullRequest, participant))
        .map(pullRequestFactory::create)
        .collect(Collectors.toList());
  }

  @Override
  public List<PullRequest> findAllOpenedPullRequests(Repository repository) {
    final GHRepository ghRepository = repositoryProvider.provide(repository);
    final Set<GitHubStatus> statutes = Sets.immutableEnumSet(GitHubStatus.OPEN);
    return pullRequestProvider.provide(ghRepository, statusConverter.convert(statutes))
        .stream()
        .map(pullRequestFactory::create)
        .collect(Collectors.toList());
  }
}
