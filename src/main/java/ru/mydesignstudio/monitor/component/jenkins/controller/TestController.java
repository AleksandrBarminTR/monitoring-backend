package ru.mydesignstudio.monitor.component.jenkins.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mydesignstudio.monitor.component.jenkins.service.job.NewPullRequestsChecker;
import ru.mydesignstudio.monitor.component.jenkins.service.job.NotCompletedJobsChecker;
import ru.mydesignstudio.monitor.component.jenkins.service.job.NotStartedJobsChecker;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
  private final NewPullRequestsChecker pullRequestsChecker;
  private final NotStartedJobsChecker notStartedJobsChecker;
  private final NotCompletedJobsChecker notCompletedJobsChecker;

  @GetMapping
  public Map<String, String> actions() {
    final Map<String, String> actions = new HashMap<>();
    actions.put("/test/find-new", "Find new PRs");
    actions.put("/test/check-current", "Check current jobs");
    actions.put("/test/check-progress", "Check jobs in progress");
    return actions;
  }

  @GetMapping("/find-new")
  public void startJob() {
    pullRequestsChecker.execute();
  }

  @GetMapping("/check-current")
  public void checkJobs() {
    notStartedJobsChecker.execute();
  }

  @GetMapping("/check-progress")
  public void checkProgress() {
    notCompletedJobsChecker.execute();
  }
}

