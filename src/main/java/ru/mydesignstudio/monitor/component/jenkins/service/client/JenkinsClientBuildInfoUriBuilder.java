package ru.mydesignstudio.monitor.component.jenkins.service.client;

import com.google.common.collect.Lists;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Component;

@Component
public class JenkinsClientBuildInfoUriBuilder {
  @SneakyThrows
  public URI build(URL jenkinsHost, String folderPath, String jobName, int buildNumber) {
    Objects.requireNonNull(jenkinsHost, "Host should not be null");
    Objects.requireNonNull(folderPath, "Folder path should be provided");
    Objects.requireNonNull(jobName, "Job name should be provided");

    return new URIBuilder()
        .setHost(jenkinsHost.getHost())
        .setPort(jenkinsHost.getPort())
        .setScheme(jenkinsHost.getProtocol())
        .setPathSegments(createSegments(folderPath, jobName, buildNumber))
        .build();
  }

  private List<String> createSegments(String folderPath, String jobName, int buildNumber) {
    final List<String> segments = Lists.newArrayList();
    for (String part : StringUtils.split(folderPath, "/")) {
      segments.add("job");
      segments.add(part);
    }
    segments.add("job");
    segments.add(jobName);
    segments.add(String.valueOf(buildNumber));
    segments.add("api");
    segments.add("xml");
    return segments;
  }
}
