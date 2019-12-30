package ru.mydesignstudio.monitor.component.jira.service.number.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AlternativeNumberExtractor implements NumberExtractor {
  @Value("${jira.ticket.alternative-regexp}")
  private String extractorRegex;
  private Pattern pattern;

  @PostConstruct
  public void init() {
    pattern = Pattern.compile(extractorRegex, Pattern.CASE_INSENSITIVE);
  }

  @Override
  public boolean matches(final String githubTitle) {
    return pattern.matcher(githubTitle).matches();
  }

  @Override
  public String extract(String githubTitle) {
    final Matcher matcher = pattern.matcher(githubTitle);
    if (!matcher.matches()) {
      throw new IllegalStateException();
    }
    return "UKBL-" + matcher.group(1);
  }
}
