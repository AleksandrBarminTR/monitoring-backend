package ru.mydesignstudio.monitor.component.pull.request.service.github;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;

@Component
@RequiredArgsConstructor
@Slf4j
public class GitHubRepositoryProvider {
  private final GitHub gitHub;

  public GHRepository provide(Repository repository) {
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
}
