package ru.mydesignstudio.monitor.component.jira.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TicketNumberExtractor.class)
@TestPropertySource(locations = "classpath:/application.properties")
class TicketNumberExtractorTest {
  @Autowired
  private TicketNumberExtractor unitUnderTest;

  @ParameterizedTest
  @CsvSource({
      "UKBL-2010 Some Ticket,UKBL-2010",
      "ukbl-2010 Some,UKBL-2010",
      "abcUKBL-2010def,UKBL-2010"
  })
  void extract_shouldExtractTicketNumber(String pullRequestTitle, String ticketNumber) {
    final Optional<String> extracted = unitUnderTest.extract(pullRequestTitle);

    assertAll(
        () -> assertNotNull(extracted, "Null is returned"),
        () -> assertTrue(extracted.isPresent(), "Value is not present"),
        () -> assertEquals(ticketNumber, extracted.get(), "Values aren't equal")
    );
  }

  @Test
  void extract_shouldNotExtractTicketNumber() {
    final Optional<String> extracted = unitUnderTest.extract("Random string");

    assertAll(
        () -> assertNotNull(extracted),
        () -> assertFalse(extracted.isPresent())
    );
  }

  @Test
  void extract_shouldThrowExceptionInCaseOfNull() {
    assertThrows(NullPointerException.class, () -> {
      unitUnderTest.extract(null);
    });
  }
}