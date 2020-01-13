package ru.mydesignstudio.monitor.component.jenkins.service.job;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;
import ru.mydesignstudio.monitor.component.pull.request.service.github.GitHubService;
import ru.mydesignstudio.monitor.component.pull.request.service.repository.RepositoryService;

@Component
@RequiredArgsConstructor
public class OpenedPullRequestsProvider {
  private final GitHubService gitHubService;
  private final RepositoryService repositoryService;

  public List<PullRequest> provide() {
    return repositoryService.findAll()
        .stream()
        // TODO, remove this filter
        .filter(repository -> repository.getRepositoryName().contains("app-editorial-new"))
        .map(repository -> gitHubService.findAllOpenedPullRequests(repository))
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }
}
