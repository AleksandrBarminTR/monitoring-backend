package ru.mydesignstudio.monitor.component.participant.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.hamcrest.object.HasEqualValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;
import ru.mydesignstudio.monitor.component.participant.model.ParticipantModel;
import ru.mydesignstudio.monitor.component.test.utils.ObjectMother;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

@ExtendWith(MockitoExtension.class)
class ParticipantToModelConverterTest {
  @InjectMocks
  private ObjectMother mother;
  @InjectMocks
  private ParticipantToModelConverter unitUnderTest;

  @Test
  @Disabled
  void convert_copiesValuesCorrectly() throws Exception {
    final Participant participant = mother.create(Participant.class);

    final ParticipantModel model = unitUnderTest.convert(participant);

    assertAll(
        () -> assertNotNull(model),
        () -> assertThat(model, new SamePropertyValuesAs<>(participant, Collections.emptyList()))
    );
  }
}