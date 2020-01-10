package ru.mydesignstudio.monitor.component.jenkins.service.client;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

@ExtendWith(MockitoExtension.class)
class BasicAuthenticationHeaderFactoryTest {
  @InjectMocks
  private BasicAuthenticationHeaderFactory unitUnderTest;

  @Test
  void check_everythingStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void build_headersShouldBeCreated() {
    final HttpHeaders headers = unitUnderTest.build("username", "password");

    assertAll(
        () -> assertNotNull(headers),
        () -> assertNotNull(headers.get("Authorization")),
        () -> assertEquals("Basic dXNlcm5hbWU6cGFzc3dvcmQ=", headers.get("Authorization").get(0))
    );
  }
}
