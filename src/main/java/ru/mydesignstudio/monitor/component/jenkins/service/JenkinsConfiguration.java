package ru.mydesignstudio.monitor.component.jenkins.service;

import com.cdancy.jenkins.rest.JenkinsApi;
import com.cdancy.jenkins.rest.JenkinsClient;
import com.cdancy.jenkins.rest.features.JobsApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JenkinsConfiguration {
  @Value("${jenkins.url}")
  private String jenkinsUrl;
  @Value("${jenkins.login}")
  private String jenkinsLogin;
  @Value("${jenkins.password}")
  private String jenkinsPassword;

  @Bean
  public JenkinsClient jenkinsClient() {
    return JenkinsClient.builder()
        .endPoint(jenkinsUrl)
        .credentials(jenkinsLogin + ":" + jenkinsPassword)
        .build();
  }

  @Bean
  public JenkinsApi jenkinsApi(JenkinsClient jenkinsClient) {
    return jenkinsClient.api();
  }

  @Bean
  public JobsApi jenkinsJobApi(JenkinsApi jenkinsApi) {
    return jenkinsApi.jobsApi();
  }
}
