package ru.mydesignstudio.monitor.component.jira.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import ru.mydesignstudio.monitor.component.jira.model.JiraResponse;
import ru.mydesignstudio.monitor.component.jira.model.JiraTicket;
import ru.mydesignstudio.monitor.component.jira.service.ticket.JiraTicketFactory;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    JiraServiceRestImpl.class
})
@TestPropertySource(locations = "classpath:/application.properties")
class JiraServiceRestImplTest {
  private Gson gson = new Gson();
  @MockBean
  private RestTemplate restTemplate;
  @MockBean
  private JiraTicketFactory ticketFactory;
  @Autowired
  private JiraService jiraService;
  @Value("classpath:/ru/mydesignstudio/monitor/component/jira/service/jira.response.json")
  private Resource jiraResponse;

  @BeforeEach
  void setUp() {
    when(restTemplate.getForObject(any(URI.class), any())).thenAnswer(invocationOnMock -> {
      try (final Reader reader = new InputStreamReader(jiraResponse.getInputStream())) {
        return gson.fromJson(reader, JiraResponse.class);
      }
    });
    when(ticketFactory.create(any(JiraResponse.class))).thenAnswer(invocationOnMock -> {
      return mock(JiraTicket.class);
    });
  }

  @Test
  void check_contextStarts() {
    assertNotNull(jiraService);
  }

  @Test
  void findTicket_shouldReturnResponse() {
    final Optional<JiraTicket> ticket = jiraService.findTicket("UKBL-2037");

    assertAll(
        () -> assertNotNull(ticket, "Ticket wasn't returned"),
        () -> assertTrue(ticket.isPresent(), "Ticket is not present")
    );
  }
}