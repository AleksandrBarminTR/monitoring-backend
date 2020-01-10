package ru.mydesignstudio.monitor.component.jenkins.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JenkinsConfiguration {
  @Value("${jenkins.url}")
  private String jenkinsUrl;
  @Value("${jenkins.login}")
  private String jenkinsLogin;
  @Value("${jenkins.password}")
  private String jenkinsPassword;

//  @Bean
//  public JenkinsClient jenkinsClient() {
//    return new JenkinsClient(jenkinsUrl, jenkinsLogin, jenkinsPassword);
//  }
}
