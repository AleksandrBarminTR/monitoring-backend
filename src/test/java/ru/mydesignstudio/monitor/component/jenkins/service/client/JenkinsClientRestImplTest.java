package ru.mydesignstudio.monitor.component.jenkins.service.client;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class JenkinsClientRestImplTest {
  @Mock
  private RestTemplate restTemplate;
  @InjectMocks
  private JenkinsClientRestImpl unitUnderTest;

  @Test
  void check_everythingStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void build_shouldCallRestTemplate() {
    final Integer buildNumber = unitUnderTest
        .build("folder1/folder2/folder3", "jobName", createParams());

    assertAll(
        () -> assertNotNull(buildNumber)
    );
  }

  private Map<String, List<String>> createParams() {
    final Map<String, List<String>> params = Maps.newHashMap();
    params.put("buildHash", Lists.newArrayList(RandomStringUtils.randomAlphabetic(10)));
    return params;
  }
}