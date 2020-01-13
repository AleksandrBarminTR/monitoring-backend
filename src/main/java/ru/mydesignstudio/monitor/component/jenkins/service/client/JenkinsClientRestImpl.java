package ru.mydesignstudio.monitor.component.jenkins.service.client;

import com.google.common.collect.Lists;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class JenkinsClientRestImpl implements JenkinsClient {
  private final URL host;
  private final String username;
  private final String password;
  private final RestTemplate restTemplate;

  private final JenkinsClientBuildWithParametersUriBuilder buildUriBuilder;
  private final JenkinsClientBuildInfoUriBuilder buildInfoUriBuilder;
  private final BasicAuthenticationHeaderFactory headerFactory;
  private final QueueNumberExtractor numberExtractor;

  @Override
  public Integer build(String jobFolders, String jobName, MultiValueMap<String, String> params) {
    Objects.requireNonNull(jobFolders, "Folder should be provided");
    Objects.requireNonNull(jobName, "Job name should be provided");
    Objects.requireNonNull(params, "Params should be provided");

    final URI startBuildUri = buildUriBuilder.build(host, jobFolders, jobName);
    final HttpHeaders httpHeaders = headerFactory.build(username, password);
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    final ResponseEntity<String> responseEntity = restTemplate
        .exchange(startBuildUri, HttpMethod.POST, new HttpEntity<>(params, httpHeaders),
            String.class);

    if (responseEntity.getStatusCodeValue() / 100 != 2) {
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
    final String location = headers.get("Location").get(0);
    return new URL(location);
  }

  @Override
  public BuildInfo buildInfo(String jobFolders, String jobName, Integer buildNumber) {
    final HttpHeaders headers = headerFactory.build(username, password);
    final HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

    final URI buildInfoUri = buildInfoUriBuilder.build(host, jobFolders, jobName, buildNumber);

    final ResponseEntity<BuildInfo> entity = restTemplate
        .exchange(buildInfoUri, HttpMethod.GET, requestEntity, BuildInfo.class);

    return entity.getBody();
  }

  @Override
  @SneakyThrows
  public BuildInfo buildInfo(int queueNumber) {
    final HttpHeaders headers = headerFactory.build(username, password);
    final HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

    // TODO, replace with builder
    final URI queueJobUrl = new URIBuilder()
        .setHost(host.getHost())
        .setPort(host.getPort())
        .setScheme(host.getProtocol())
        .setPathSegments(createSegments(queueNumber))
        .build();

    final ResponseEntity<String> response;
    try {
      response = restTemplate
          .exchange(queueJobUrl, HttpMethod.GET, requestEntity, String.class);
    } catch (HttpClientErrorException.NotFound exception) {
      return new BuildInfo("COMPLETED_UNKNOWN_RESULTS");
    }

    // TODO, brush it up
    final String body = response.getBody();
    if (body.startsWith("<leftItem")) {

      final JAXBContext context = JAXBContext.newInstance(JenkinsQueueInfo.class);
      final Unmarshaller unmarshaller = context.createUnmarshaller();
      final JenkinsQueueInfo queueInfo = (JenkinsQueueInfo) unmarshaller
          .unmarshal(new StringReader(body));

      return new BuildInfo("IN_PROGRESS", queueInfo.getExecutable().getNumber());
    }

    return new BuildInfo("NOT_STARTED");
  }

  private List<String> createSegments(int queueNumber) {
    return Lists.newArrayList("queue", "item", String.valueOf(queueNumber) ,"api", "xml");
  }
}
