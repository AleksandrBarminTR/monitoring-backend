package ru.mydesignstudio.monitor.component.pull.request.service.github;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHPullRequest;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;

@Slf4j
@Component
public class GitHubPullRequestParticipantFilter implements GitHubPullRequestFilter<Participant> {
  @Override
  public boolean test(GHPullRequest pullRequest, Participant participant) {
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
}
