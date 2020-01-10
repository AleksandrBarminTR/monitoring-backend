package ru.mydesignstudio.monitor.component.jenkins.service.client;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class JenkinsClientRestImplTest {

  @Mock
  private BuildNumberExtractor numberExtractor;
  @Mock
  private BasicAuthenticationHeaderFactory headerFactory;
  @Mock
  private JenkinsClientBuildWithParametersUriBuilder buildUriBuilder;
  @Mock
  private RestTemplate restTemplate;
  @InjectMocks
  private JenkinsClientRestImpl unitUnderTest;

  @BeforeEach
  void setUp() throws Exception {
    ReflectionTestUtils.setField(unitUnderTest, "host", new URL("http://localhost:8080/"));
    ReflectionTestUtils.setField(unitUnderTest, "username", "username");
    ReflectionTestUtils.setField(unitUnderTest, "password", "password");
  }

  @Test
  void check_everythingStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void build_shouldCallRestTemplate() {
    final URI jobUri = URI.create("http://localhost:8080/job/folder1/build/buildWithParameters");
    final HttpHeaders redirectHeaders = new HttpHeaders();
    redirectHeaders.set("Location", "http://localhost:8080/job/folder1/build/42/");

    when(buildUriBuilder.build(any(URL.class), anyString(), anyString())).thenReturn(jobUri);
    when(restTemplate
        .exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
        .thenReturn(
            new ResponseEntity(redirectHeaders, HttpStatus.PERMANENT_REDIRECT));
    when(numberExtractor.extract(any(URL.class))).thenReturn(42);

    final Integer buildNumber = unitUnderTest
        .build("folder1/folder2/folder3", "jobName", createParams());

    verify(headerFactory, times(1)).build(anyString(), anyString());
    verify(restTemplate, times(1))
        .exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class));
    verify(numberExtractor, times(1)).extract(any(URL.class));

    assertAll(
        () -> assertNotNull(buildNumber),
        () -> assertEquals(42, buildNumber)
    );
  }

  @Test
  void build_ifNotARedirectAnExceptionShouldBeThrown() {
    final URI jobUri = URI.create("http://localhost:8080/job/folder1/build/buildWithParameters");
    when(restTemplate
        .exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
        .thenReturn(ResponseEntity.ok(123));
    when(buildUriBuilder.build(any(URL.class), anyString(), anyString())).thenReturn(jobUri);

    assertThrows(RuntimeException.class, () -> {
      unitUnderTest.build("folder1/folder2", "jobName", createParams());
    });
  }

  @Test
  void build_ifNoLocationHeaderAnExceptionShouldBeThrown() {
    final URI jobUri = URI.create("http://localhost:8080/job/folder1/build/buildWithParameters");
    when(restTemplate
        .exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
        .thenReturn(new ResponseEntity(HttpStatus.PERMANENT_REDIRECT));
    when(buildUriBuilder.build(any(URL.class), anyString(), anyString())).thenReturn(jobUri);

    assertThrows(RuntimeException.class, () -> {
      unitUnderTest.build("folder1/folder2", "jobName", createParams());
    });
  }

  private Map<String, List<String>> createParams() {
    final Map<String, List<String>> params = Maps.newHashMap();
    params.put("buildHash", Lists.newArrayList(RandomStringUtils.randomAlphabetic(10)));
    return params;
  }
}
