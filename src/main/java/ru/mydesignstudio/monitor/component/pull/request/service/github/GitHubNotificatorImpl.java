package ru.mydesignstudio.monitor.component.pull.request.service.github;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;

@Component
@RequiredArgsConstructor
public class GitHubNotificatorImpl implements GitHubNotificator {
  private final GitHubRepositoryProvider repositoryProvider;

  @Override
  @SneakyThrows
  public void sendNotification(PullRequest pullRequest, String message) {
    final GHRepository repository = repositoryProvider.provide(pullRequest.getRepository());
    final GHPullRequest ghPullRequest = repository.getPullRequest(pullRequest.getPullRequestId());

    ghPullRequest.comment(message);
  }
}
