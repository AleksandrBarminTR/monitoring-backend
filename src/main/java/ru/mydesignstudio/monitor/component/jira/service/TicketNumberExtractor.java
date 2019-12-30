package ru.mydesignstudio.monitor.component.jira.service;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TicketNumberExtractor {
  @Value("${jira.ticket.regexp}")
  private String ticketRegexp;
  private Pattern pattern;

  @PostConstruct
  public void init() {
    pattern = Pattern.compile(ticketRegexp, Pattern.CASE_INSENSITIVE);
  }

  public Optional<String> extract(final String pullRequestTitle) {
    Objects.requireNonNull(pullRequestTitle, "Value should not be null");

    final Matcher matcher = pattern.matcher(pullRequestTitle);
    if (!matcher.matches()) {
      return Optional.empty();
    }
    return Optional.of(matcher.group(1).toUpperCase());
  }
}
