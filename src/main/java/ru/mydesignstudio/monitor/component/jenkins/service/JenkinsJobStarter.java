package ru.mydesignstudio.monitor.component.jenkins.service;

import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;

public interface JenkinsJobStarter {
  JenkinsJob start(PullRequest pullRequest);
}
