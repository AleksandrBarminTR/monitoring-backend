package ru.mydesignstudio.monitor.component.jenkins.service.client;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueNumberExtractor {
  private final Pattern JOB_PATTER = Pattern.compile(".+/item/(\\d+)/");

  public Integer extract(URL locationHeader) {
    final String locationUrl = locationHeader.toExternalForm();
    final Matcher matcher = JOB_PATTER.matcher(locationUrl);
    if (matcher.matches()) {
      return Integer.parseInt(matcher.group(1));
    }
    throw new IllegalArgumentException();
  }

//  @SneakyThrows
//  public Integer extract(URL queueJobUrl) {
//    Objects.requireNonNull(queueJobUrl, "Queue Job Url should be provided");
//
//    boolean started = false;
//    int waiting = 30;
//    String body = "";
//    do {
//      final HttpHeaders headers = authenticationHeaderFactory.build(jenkinsLogin, jenkinsPassword);
//      final HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
//
//      final ResponseEntity<String> response = restTemplate
//          .exchange(queueJobUrl.toURI(), HttpMethod.GET, requestEntity, String.class);
//
//      body = response.getBody();
//      if (body.startsWith("<leftItem")) {
//        started = true;
//      }
//
//      Thread.sleep(1000);
//
//      waiting--;
//    } while (started == false && waiting > 0);
//
//    if (!started && waiting == 0) {
//      throw new IllegalArgumentException("Job hasn't been started");
//    }
//
//    final JAXBContext context = JAXBContext.newInstance(JenkinsQueueInfo.class);
//    final Unmarshaller unmarshaller = context.createUnmarshaller();
//    final JenkinsQueueInfo unmarshal = (JenkinsQueueInfo) unmarshaller.unmarshal(new StringReader(body));
//
//    return unmarshal.getExecutable().getN;
//  }
}
