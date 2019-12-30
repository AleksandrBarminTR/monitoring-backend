package ru.mydesignstudio.monitor.component.participant.converter;

import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;
import ru.mydesignstudio.monitor.component.participant.model.ParticipantModel;

@Component
public class ParticipantToModelConverter implements Converter<Participant, ParticipantModel> {

  @Override
  @SneakyThrows
  public ParticipantModel convert(Participant participant) {
    final ParticipantModel model = new ParticipantModel();
    BeanUtils.copyProperties(model, participant);
    return model;
  }
}
