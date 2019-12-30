package ru.mydesignstudio.monitor.component.participant.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mydesignstudio.monitor.component.participant.model.ParticipantModel;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ParticipantControllerTest {
  @Autowired
  private ParticipantController unitUnderTest;

  @Test
  void contextStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void participants_areConverted() {
    final List<ParticipantModel> models = unitUnderTest.findAll();

    assertAll(
        () -> assertNotNull(models),
        () -> assertFalse(models.isEmpty())
    );
  }
}
