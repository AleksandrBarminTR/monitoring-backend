package ru.mydesignstudio.monitor.component.jenkins.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;

@Component
public class JenkinsJobFolderPathExtractor {
  public String extract(Repository repository) {
    String urlString = repository.getRepositoryUrl().toExternalForm();

    urlString = StringUtils.substringAfter(urlString, "job/");
    urlString = StringUtils.substringBeforeLast(urlString, "job/");

    return urlString;
  }
}
