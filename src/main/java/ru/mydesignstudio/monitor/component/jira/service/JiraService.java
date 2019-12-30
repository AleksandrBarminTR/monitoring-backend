package ru.mydesignstudio.monitor.component.jira.service;

import java.util.Optional;
import ru.mydesignstudio.monitor.component.jira.model.JiraTicket;

public interface JiraService {
  Optional<JiraTicket> findTicket(String ticket);
}
