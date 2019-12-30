package ru.mydesignstudio.monitor.component.monitor.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mydesignstudio.monitor.component.jira.model.JiraTicket;
import ru.mydesignstudio.monitor.component.jira.service.JiraService;
import ru.mydesignstudio.monitor.component.jira.service.TicketNumberExtractor;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorRequest;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorResponse;
import ru.mydesignstudio.monitor.component.participant.service.ParticipantService;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;
import ru.mydesignstudio.monitor.component.pull.request.service.GitHubService;

@Service
@RequiredArgsConstructor
public class MonitorServiceImpl implements MonitorService {
  private final ParticipantService participantService;
  private final GitHubService gitHubService;
  private final TicketNumberExtractor ticketNumberExtractor;
  private final JiraService jiraService;

  @Override
  public List<MonitorResponse> process(final MonitorRequest request) {
    return participantService.findByLogin(request.getParticipantName())
        .map(participant -> gitHubService.findPullRequests(participant, request.getStatuses()))
        .map(this::toMonitorResponse)
        .orElseThrow(() -> new RuntimeException());
  }

  private List<MonitorResponse> toMonitorResponse(List<PullRequest> pullRequests) {
    return pullRequests.stream()
        .map(this::toMonitorResponse)
        .map(this::withJiraInfo)
        .collect(Collectors.toList());
  }

  private MonitorResponse withJiraInfo(MonitorResponse monitorResponse) {
    final Optional<JiraTicket> jiraTicket = ticketNumberExtractor
        .extract(monitorResponse.getGithubTitle())
        .flatMap(number -> jiraService.findTicket(number));

    monitorResponse.setJiraResolved(jiraTicket.isPresent());
    if (jiraTicket.isPresent()) {
      final JiraTicket jira = jiraTicket.get();
      monitorResponse.setJiraLink(jira.getLink());
      monitorResponse.setJiraStatus(jira.getStatus());
    }

    return monitorResponse;
  }

  private MonitorResponse toMonitorResponse(PullRequest request) {
    return MonitorResponse.builder()
        .githubLink(request.getUrl().toExternalForm())
        .githubStatus(request.getStatus())
        .githubTitle(request.getTitle())
        .build();
  }
}
