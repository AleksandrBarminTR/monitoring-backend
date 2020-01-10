package ru.mydesignstudio.monitor.component.jenkins.service.client;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class JenkinsClientRestImpl implements JenkinsClient {
  private final URL host;
  private final String username;
  private final String password;
  private final RestTemplate restTemplate;

  private final JenkinsClientBuildWithParametersUriBuilder buildUriBuilder;
  private final BasicAuthenticationHeaderFactory headerFactory;
  private final BuildNumberExtractor numberExtractor;

  @Override
  public Integer build(String jobFolders, String jobName, Map<String, List<String>> params) {
    Objects.requireNonNull(jobFolders, "Folder should be provided");
    Objects.requireNonNull(jobName, "Job name should be provided");
    Objects.requireNonNull(params, "Params should be provided");

    final URI startBuildUri = buildUriBuilder.build(host, jobFolders, jobName);
    final HttpHeaders httpHeaders = headerFactory.build(username, password);

    final ResponseEntity<String> responseEntity = restTemplate
        .exchange(startBuildUri, HttpMethod.POST, new HttpEntity<>(params, httpHeaders),
            String.class);

    if (responseEntity.getStatusCodeValue() / 100 != 3) {
      throw new RuntimeException("Something went wrong, doesn't work: " + responseEntity.toString());
    }

    final HttpHeaders headers = responseEntity.getHeaders();
    if (!headers.containsKey("Location")) {
      throw new RuntimeException("There is no Location header in the response");
    }

    return numberExtractor.extract(getLocationHeader(headers));
  }

  @SneakyThrows
  private URL getLocationHeader(HttpHeaders headers) {
    return new URL(headers.get("Location").get(0));
  }

  @Override
  public BuildInfo buildInfo(String jobFolders, String jobName, Integer buildNumber) {
    throw new UnsupportedOperationException();
  }
}
