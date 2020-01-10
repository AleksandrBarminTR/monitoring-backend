package ru.mydesignstudio.monitor.component.jenkins.service;

import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJobStatus;

public interface JenkinsJobStatusChecker {
  JenkinsJobStatus check(JenkinsJob jenkinsJob);
}
