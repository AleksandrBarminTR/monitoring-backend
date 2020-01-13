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

import java.net.URI;
import java.net.URL;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class JenkinsClientRestImplTest {

  @Mock
  private QueueNumberExtractor numberExtractor;
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

    when(headerFactory.build(anyString(), anyString())).thenReturn(new HttpHeaders());
    when(buildUriBuilder.build(any(URL.class), anyString(), anyString())).thenReturn(jobUri);
    when(restTemplate
        .exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
        .thenReturn(
            new ResponseEntity(redirectHeaders, HttpStatus.CREATED));
    when(numberExtractor.extract(any(URL.class))).thenReturn(42);

    final Integer executable = unitUnderTest
        .build("folder1/folder2/folder3", "jobName", createParams());

    verify(headerFactory, times(1)).build(anyString(), anyString());
    verify(restTemplate, times(1))
        .exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class));
    verify(numberExtractor, times(1)).extract(any(URL.class));

    assertAll(
        () -> assertNotNull(executable),
        () -> assertEquals(42, executable)
    );
  }

  @Test
  void build_ifNotARedirectAnExceptionShouldBeThrown() {
    final URI jobUri = URI.create("http://localhost:8080/job/folder1/build/buildWithParameters");
    when(headerFactory.build(anyString(), anyString())).thenReturn(new HttpHeaders());
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
    when(headerFactory.build(anyString(), anyString())).thenReturn(new HttpHeaders());
    when(restTemplate
        .exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
        .thenReturn(new ResponseEntity(HttpStatus.CREATED));
    when(buildUriBuilder.build(any(URL.class), anyString(), anyString())).thenReturn(jobUri);

    assertThrows(RuntimeException.class, () -> {
      unitUnderTest.build("folder1/folder2", "jobName", createParams());
    });
  }

  private MultiValueMap<String, String> createParams() {
    final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("buildHash", RandomStringUtils.randomAlphabetic(10));
    return params;
  }
}
