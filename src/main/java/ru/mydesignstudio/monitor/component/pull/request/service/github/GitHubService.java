package ru.mydesignstudio.monitor.component.pull.request.service.github;

import java.util.List;
import java.util.Set;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;
import ru.mydesignstudio.monitor.component.pull.request.model.GitHubStatus;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;

public interface GitHubService {
  List<PullRequest> findAllOpenedPullRequests(Participant participant);

  List<PullRequest> findAllClosedPullRequests(Participant participant);

  List<PullRequest> findAllPullRequests(Participant participant);

  List<PullRequest> findPullRequests(Participant participant, Set<GitHubStatus> statuses);

  List<PullRequest> findAllOpenedPullRequests(Repository repository);
}
