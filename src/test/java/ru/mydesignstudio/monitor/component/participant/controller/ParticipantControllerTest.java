package ru.mydesignstudio.monitor.component.participant.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;
import ru.mydesignstudio.monitor.component.participant.model.ParticipantModel;
import ru.mydesignstudio.monitor.component.participant.service.ParticipantService;

@ExtendWith(MockitoExtension.class)
class ParticipantControllerTest {
  @Mock
  private ParticipantService participantService;
  @Mock
  private ConversionService conversionService;
  @Mock
  private Participant participant;
  @Mock
  private ParticipantModel participantModel;
  @InjectMocks
  private ParticipantController unitUnderTest;

  @Test
  void contextStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void participants_areConverted() {
    when(participantService.findAll()).thenReturn(Collections.singletonList(participant));
    when(conversionService.convert(participant, ParticipantModel.class)).thenReturn(participantModel);

    final List<ParticipantModel> models = unitUnderTest.findAll();

    assertAll(
        () -> assertNotNull(models),
        () -> assertFalse(models.isEmpty())
    );
  }
}
