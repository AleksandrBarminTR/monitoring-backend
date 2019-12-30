package ru.mydesignstudio.monitor.component.jira.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mydesignstudio.monitor.component.jira.model.JiraResponse;
import ru.mydesignstudio.monitor.component.jira.model.JiraTicket;
import ru.mydesignstudio.monitor.component.jira.service.link.JiraLinkFactory;
import ru.mydesignstudio.monitor.component.jira.service.ticket.JiraTicketFactory;
import ru.mydesignstudio.monitor.component.jira.service.ticket.JiraTicketStatusFactory;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    JiraTicketFactory.class,
    JiraTicketStatusFactory.class,
    JiraLinkFactory.class
})
@TestPropertySource("classpath:/application.properties")
class JiraTicketFactoryTest {

  private Gson gson = new Gson();

  @Autowired
  private JiraTicketFactory unitUnderTest;
  @Value("classpath:/ru/mydesignstudio/monitor/component/jira/service/jira.response.json")
  private Resource jiraResponseContent;

  private JiraResponse jiraResponse;

  @BeforeEach
  void setUp() throws Exception {
    try (final Reader reader = new InputStreamReader(jiraResponseContent.getInputStream())) {
      jiraResponse = gson.fromJson(reader, JiraResponse.class);
    }
  }

  @Test
  void create_shouldCreateTicket() {
    final JiraTicket ticket = unitUnderTest.create(jiraResponse);

    assertAll(
        () -> assertNotNull(ticket),
        () -> assertEquals("UKBL-2037", ticket.getTicket()),
        () -> assertEquals(" During document ingest, OJ is added every other time",
            ticket.getDescription())
    );
  }
}