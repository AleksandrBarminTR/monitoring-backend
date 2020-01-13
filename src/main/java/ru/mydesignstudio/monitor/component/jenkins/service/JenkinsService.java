package ru.mydesignstudio.monitor.component.jenkins.service;

import java.util.List;
import java.util.Optional;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJobStatus;

public interface JenkinsService {
  Optional<JenkinsJob> findOneByHeadSha(String headHash);

  JenkinsJob save(JenkinsJob jenkinsJob);

  List<JenkinsJob> findJobsByStatus(JenkinsJobStatus status);

  List<JenkinsJob> findUnstartedJobs();
}
