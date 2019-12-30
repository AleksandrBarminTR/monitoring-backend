package ru.mydesignstudio.monitor.component.pull.request.service;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;

@Component
@RequiredArgsConstructor
public class RepositoryFactory {
  private final UrlFactory urlFactory;
  private Pattern pattern;

  @PostConstruct
  public void init() {
    pattern = Pattern.compile("https://github.com/(.+)/(.+)(\\.git)?");
  }

  public Repository create(String repositoryUrl) {
    final Matcher matcher = pattern.matcher(repositoryUrl);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid repository url");
    }
    final String organization = matcher.group(1);
    final String repositoryName = matcher.group(2).replace(".git", "");

    return new Repository(organization + "/" + repositoryName, urlFactory.create(repositoryUrl));
  }
}
