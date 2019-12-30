package ru.mydesignstudio.monitor.component.participant.service;

import java.util.List;
import java.util.Optional;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;

public interface ParticipantService {
  List<Participant> findAll();

  Optional<Participant> findByLogin(String login);
}
