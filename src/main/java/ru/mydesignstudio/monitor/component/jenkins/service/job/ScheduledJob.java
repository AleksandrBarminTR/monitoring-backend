package ru.mydesignstudio.monitor.component.jenkins.service.job;

@FunctionalInterface
public interface ScheduledJob {
  void execute();
}
