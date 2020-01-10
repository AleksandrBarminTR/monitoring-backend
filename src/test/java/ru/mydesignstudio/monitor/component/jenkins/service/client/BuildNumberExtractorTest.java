package ru.mydesignstudio.monitor.component.jenkins.service.client;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BuildNumberExtractorTest {
  @InjectMocks
  private BuildNumberExtractor unitUnderTest;

  @Test
  void check_everythingStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void extract_shouldExtractCorrectly() throws Exception {
    final Integer extracted = unitUnderTest.extract(new URL("http://localhost:8080/build/42/"));

    assertAll(
        () -> assertNotNull(extracted),
        () -> assertEquals(42, extracted)
    );
  }
}
