package ru.mydesignstudio.monitor.component.jira.service.ticket;

import static org.junit.jupiter.api.Assertions.*;


import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.context.internal.JTASessionContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.mydesignstudio.monitor.component.jira.model.JiraTicketStatus;

/**
 * Created on: 1/15/2020 By: UC231651
 * Copyright 2020: Thomson Reuters Global Resources. All Rights Reserved.
 * Proprietary and Confidential information of TRGR.
 * Disclosure, Use or Reproduction without the written authorization of TRGR is prohibited.
 */
@ExtendWith(MockitoExtension.class)
class JiraTicketStatusFactoryTest {
  @InjectMocks
  private JiraTicketStatusFactory unitUnderTest;

  @Test
  public void resolve_shouldReturnUnknown() {
    final JiraTicketStatus result = unitUnderTest
        .create(RandomStringUtils.randomAlphabetic(10));

    assertEquals(JiraTicketStatus.UNKNOWN, result);
  }

  @Test
  public void resolve_open() {
    final JiraTicketStatus result = unitUnderTest.create("Open");

    assertEquals(JiraTicketStatus.OPEN, result);
  }
}