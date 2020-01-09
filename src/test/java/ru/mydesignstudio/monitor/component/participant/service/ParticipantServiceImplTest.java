package ru.mydesignstudio.monitor.component.participant.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.Gson;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    ParticipantServiceImpl.class,
    Gson.class
})
class ParticipantServiceImplTest {
  @Autowired
  private ParticipantService unitUnderTest;

  @Test
  void check_contextStarts() {
    final List<Participant> participants = unitUnderTest.findAll();

    assertAll(
        () -> assertNotNull(participants),
        () -> assertFalse(participants.isEmpty())
    );
  }
}
