package ru.mydesignstudio.monitor.component.pull.request.service.github;

import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GitHubPullRequestProvider {
  public List<GHPullRequest> provide(GHRepository repository, GHIssueState issueState) {
    try {
      log.debug("Trying to get pull request for repository {}", repository.getUrl());
      return repository.getPullRequests(issueState);
    } catch (IOException e) {
      log.error("Can't get pull requests for repository {}", repository.getUrl(), e);
      throw new RuntimeException("Can't get pull requests", e);
    } finally {
      log.debug("Pull requests for repository {} retrieved", repository.getUrl());
    }
  }
}
