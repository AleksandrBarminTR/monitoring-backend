package ru.mydesignstudio.monitor.component.jenkins.service.client;

import java.net.URL;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class BuildNumberExtractor {
  private static final Pattern PATTERN = Pattern.compile(".+/(\\d+)/");

  public Integer extract(URL redirection) {
    Objects.requireNonNull(redirection, "Redirection should be provided");

    final String urlString = redirection.toExternalForm();
    final Matcher matcher = PATTERN.matcher(urlString);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("URL has invalid format");
    }
    final String buildString = matcher.group(1);

    return Integer.parseInt(buildString);
  }
}
