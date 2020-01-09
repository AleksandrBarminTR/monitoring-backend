package ru.mydesignstudio.monitor.component.monitor.service.enhancer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.jira.model.JiraTicket;
import ru.mydesignstudio.monitor.component.jira.model.JiraTicketStatus;
import ru.mydesignstudio.monitor.component.jira.service.JiraService;
import ru.mydesignstudio.monitor.component.jira.service.number.extractor.TicketNumberExtractor;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorResponse;
import ru.mydesignstudio.monitor.component.monitor.model.MonitorResponse.MonitorResponseBuilder;
import ru.mydesignstudio.monitor.component.pull.request.model.PullRequest;

@ExtendWith(MockitoExtension.class)
class JiraEnhancerTest {
  @Mock
  private TicketNumberExtractor ticketNumberExtractor;
  @Mock
  private PullRequest request;
  @Mock
  private JiraService jiraService;
  @Mock
  private JiraTicket jiraTicket;
  @InjectMocks
  private JiraEnhancer unitUnderTest;

  @Test
  void enhance_shouldReturnEnhancedResponse() throws Exception {
    final MonitorResponseBuilder builder = MonitorResponse.builder();

    final String ticketTitle = RandomStringUtils.randomAlphabetic(10);
    final String ticketNumber = RandomStringUtils.randomAlphabetic(10);
    final String jiraLink = "http://" + RandomStringUtils.randomAlphabetic(10);
    final String jiraDescription = RandomStringUtils.randomAlphabetic(10);

    when(request.getTitle()).thenReturn(ticketTitle);
    when(ticketNumberExtractor.extract(ticketTitle)).thenReturn(Optional.of(ticketNumber));
    when(jiraService.findTicket(ticketNumber)).thenReturn(Optional.of(jiraTicket));
    when(jiraTicket.getLink()).thenReturn(new URL(jiraLink));
    when(jiraTicket.getStatus()).thenReturn(JiraTicketStatus.OPEN);
    when(jiraTicket.getDescription()).thenReturn(jiraDescription);

    unitUnderTest.enhance(builder, request);

    final MonitorResponse response = builder.build();

    assertAll(
        () -> assertNotNull(response),
        () -> assertTrue(response.isJiraResolved()),
        () -> assertEquals(jiraLink, response.getJiraLink()),
        () -> assertEquals(JiraTicketStatus.OPEN, response.getJiraStatus()),
        () -> assertEquals(jiraDescription, response.getJiraTitle())
    );
  }
}