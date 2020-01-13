package ru.mydesignstudio.monitor.component.monitor.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorRequest;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorResponse;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorResponse.MonitorResponseBuilder;
import ru.mydesignstudio.monitor.component.monitor.service.enhancer.MonitorResponseEnhancer;
import ru.mydesignstudio.monitor.component.participant.service.ParticipantService;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;
import ru.mydesignstudio.monitor.component.pull.request.service.github.GitHubService;

@Service
@RequiredArgsConstructor
public class MonitorServiceImpl implements MonitorService {
  private final List<MonitorResponseEnhancer> enhancers;
  private final ParticipantService participantService;
  private final GitHubService gitHubService;

  @Override
  public List<MonitorResponse> process(final MonitorRequest request) {
    return participantService.findByLogin(request.getParticipantName())
        .map(participant -> gitHubService.findPullRequests(participant, request.getStatuses()))
        .map(this::toMonitorResponse)
        .orElseThrow(() -> new RuntimeException());
  }

  private List<MonitorResponse> toMonitorResponse(List<PullRequest> pullRequests) {
    final List<MonitorResponse> responses = new ArrayList<>();
    for (final PullRequest request : pullRequests) {
      final MonitorResponseBuilder builder = MonitorResponse.builder();

      enhancers.stream()
          .forEach(action -> action.enhance(builder, request));

      responses.add(builder.build());
    }
    return responses;
  }
}
