package ru.mydesignstudio.monitor.component.pull.request.service.github;

import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;

public interface GitHubNotificator {
  void sendNotification(PullRequest pullRequest, String message);
}
