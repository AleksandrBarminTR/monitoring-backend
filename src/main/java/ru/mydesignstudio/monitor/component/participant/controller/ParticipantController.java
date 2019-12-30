package ru.mydesignstudio.monitor.component.participant.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mydesignstudio.monitor.component.participant.model.ParticipantModel;
import ru.mydesignstudio.monitor.component.participant.service.ParticipantService;

@RestController
@RequestMapping("/participants")
@RequiredArgsConstructor
public class ParticipantController {
  private final ParticipantService participantService;
  private final ConversionService conversionService;

  @GetMapping
  public List<ParticipantModel> findAll() {
    return participantService.findAll().stream()
        .map(participant -> conversionService.convert(participant, ParticipantModel.class))
        .collect(Collectors.toList());
  }

}
