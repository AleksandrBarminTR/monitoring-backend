package ru.mydesignstudio.monitor.component.participant.service;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.mydesignstudio.monitor.component.participant.entity.Participant;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
  private final Gson gson;

  private List<Participant> participants;
  @Value("classpath:/participants.json")
  private Resource participantsList;

  @PostConstruct
  public void init() {
    try (final Reader reader = new InputStreamReader(participantsList.getInputStream())) {
      participants = Arrays.asList(
          gson.fromJson(reader, Participant[].class)
      );
      participants.sort((left, right) -> {
        return StringUtils.compare(left.getName(), right.getName());
      });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Participant> findAll() {
    return participants;
  }

  @Override
  public Optional<Participant> findByLogin(String login) {
    return findAll().stream()
        .filter(participant -> login.equals(participant.getLogin()))
        .findAny();
  }
}
