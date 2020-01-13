package ru.mydesignstudio.monitor.component.jenkins.service;

import java.net.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.mydesignstudio.monitor.component.jenkins.service.client.BasicAuthenticationHeaderFactory;
import ru.mydesignstudio.monitor.component.jenkins.service.client.JenkinsClient;
import ru.mydesignstudio.monitor.component.jenkins.service.client.JenkinsClientBuildInfoUriBuilder;
import ru.mydesignstudio.monitor.component.jenkins.service.client.JenkinsClientBuildWithParametersUriBuilder;
import ru.mydesignstudio.monitor.component.jenkins.service.client.JenkinsClientRestImpl;
import ru.mydesignstudio.monitor.component.jenkins.service.client.QueueNumberExtractor;

@Configuration
public class JenkinsConfiguration {

  @Value("${jenkins.url}")
  private URL jenkinsUrl;
  @Value("${jenkins.login}")
  private String jenkinsLogin;
  @Value("${jenkins.password}")
  private String jenkinsPassword;

  @Bean
  public JenkinsClient jenkinsClient(
      RestTemplate restTemplate,
      JenkinsClientBuildWithParametersUriBuilder buildUriBuilder,
      JenkinsClientBuildInfoUriBuilder buildInfoUriBuilder,
      BasicAuthenticationHeaderFactory headerFactory,
      QueueNumberExtractor numberExtractor) {
    return new JenkinsClientRestImpl(jenkinsUrl, jenkinsLogin, jenkinsPassword, restTemplate,
        buildUriBuilder, buildInfoUriBuilder, headerFactory, numberExtractor);
  }
}
