package ru.mydesignstudio.monitor.component.jenkins.service;

import java.util.Optional;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;

public interface JenkinsService {
  Optional<JenkinsJob> findOneByHeadSha(String headHash);

  JenkinsJob save(JenkinsJob jenkinsJob);
}
