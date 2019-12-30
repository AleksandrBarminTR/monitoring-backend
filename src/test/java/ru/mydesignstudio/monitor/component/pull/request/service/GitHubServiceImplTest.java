package ru.mydesignstudio.monitor.component.pull.request.service;

import java.util.Collections;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mydesignstudio.monitor.component.pull.request.model.GitHubStatus;
import ru.mydesignstudio.monitor.component.participant.service.ParticipantService;
import ru.mydesignstudio.monitor.component.pull.request.service.github.GitHubService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class GitHubServiceImplTest {
  @Autowired
  private ParticipantService participantService;
  @Autowired
  private GitHubService unitUnderTest;

  @Test
  @Disabled
  void findAll_shouldLoadAtLeastSomething() {
    participantService.findByLogin("paul-parkins-tr")
        .map(participant -> unitUnderTest.findPullRequests(participant, Collections.singleton(
            GitHubStatus.OPEN)))
        .ifPresent(pullRequests -> {
          System.out.println(pullRequests);
        });
  }
}
