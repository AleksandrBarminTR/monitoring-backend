package ru.mydesignstudio.monitor.component.jenkins.service;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.repository.JenkinsRepository;

@Service
@RequiredArgsConstructor
public class JenkinsServiceImpl implements JenkinsService {
  private final JenkinsRepository jenkinsRepository;

  @Override
  public Optional<JenkinsJob> findOneByHeadSha(String headHash) {
    Objects.requireNonNull(headHash, "Hash should not be null");

    return jenkinsRepository.findFirstByHeadHash(headHash);
  }

  @Override
  public JenkinsJob save(JenkinsJob jenkinsJob) {
    throw new UnsupportedOperationException();
  }
}
