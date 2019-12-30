package ru.mydesignstudio.monitor.component.jira.service.number.extractor;

public interface NumberExtractor {
  boolean matches(String githubTitle);

  String extract(String githubTitle);
}
