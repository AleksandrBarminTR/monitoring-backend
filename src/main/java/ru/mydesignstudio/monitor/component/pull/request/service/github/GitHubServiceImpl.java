package ru.mydesignstudio.monitor.component.pull.request.service.github;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Service;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;
import ru.mydesignstudio.monitor.component.pull.request.model.GitHubStatus;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;
import ru.mydesignstudio.monitor.component.pull.request.service.repository.RepositoryService;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubServiceImpl implements GitHubService {
  private final RepositoryService repositoryService;
  private final PullRequestFactory pullRequestFactory;
  private final GitHub gitHub;

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
    return repositoryService.findAll().parallelStream()
        .map(this::getGitHubRepository)
        .flatMap(repository -> getPullRequests(repository, getIssueState(statuses)))
        .filter(pullRequest -> isCreatedBy(pullRequest, participant))
        .map(pullRequestFactory::create)
        .collect(Collectors.toList());
  }

  private boolean isCreatedBy(GHPullRequest pullRequest, Participant participant) {
    try {
      log.debug("Check if pull request {} belongs to user {}", pullRequest.getUrl(), participant.getLogin());
      return pullRequest.getUser().getLogin().equals(participant.getLogin());
    } catch (IOException e) {
      log.error("Can't get author for the pull request {}", pullRequest.getUrl());
      throw new RuntimeException("Can't get author for pull request", e);
    } finally {
      log.debug("Pull request {} checked", pullRequest.getUrl());
    }
  }

  private GHRepository getGitHubRepository(Repository repository) {
    try {
      log.debug("Start retrieving repository {}", repository.getRepositoryUrl());
      return gitHub.getRepository(repository.getRepositoryName());
    } catch (IOException e) {
      log.error("Can't get repository {}", repository.getRepositoryUrl(), e);
      throw new RuntimeException("Can't get repository", e);
    } finally {
      log.debug("Repository {} retrieved", repository.getRepositoryUrl());
    }
  }

  private Stream<GHPullRequest> getPullRequests(GHRepository repository, GHIssueState issueState) {
    try {
      log.debug("Trying to get pull request for repository {}", repository.getUrl());
      return repository.getPullRequests(issueState)
          .stream();
    } catch (IOException e) {
      log.error("Can't get pull requests for repository {}", repository.getUrl(), e);
      throw new RuntimeException("Can't get pull requests", e);
    } finally {
      log.debug("Pull requests for repository {} retrieved", repository.getUrl());
    }
  }

  private GHIssueState getIssueState(Set<GitHubStatus> statuses) {
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
