package ru.mydesignstudio.monitor.component.pull.request.service.github;

import org.kohsuke.github.GHPullRequest;

public interface GitHubPullRequestFilter<T> {
  boolean test(GHPullRequest request, T parameter);
}
