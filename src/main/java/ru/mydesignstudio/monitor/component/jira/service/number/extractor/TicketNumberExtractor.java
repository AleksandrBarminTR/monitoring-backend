package ru.mydesignstudio.monitor.component.jira.service.number.extractor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketNumberExtractor {
  private final List<NumberExtractor> extractors;

  public Optional<String> extract(final String pullRequestTitle) {
    Objects.requireNonNull(pullRequestTitle, "Value should not be null");

    return extractors.stream()
        .filter(extractor -> extractor.matches(pullRequestTitle))
        .map(extractors -> extractors.extract(pullRequestTitle))
        .findFirst();
  }
}
