package ru.mydesignstudio.monitor.component.pull.request.service.github;

import java.io.IOException;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitHubConfiguration {
  @Value("${github.username}")
  private String username;
  @Value("${github.password}")
  private String password;

  @Bean
  public GitHub gitHub() throws IOException {
    return new GitHubBuilder()
        .withPassword(username, password)
        .build();
  }
}
