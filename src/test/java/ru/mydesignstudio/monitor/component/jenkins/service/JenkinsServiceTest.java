package ru.mydesignstudio.monitor.component.jenkins.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.repository.JenkinsRepository;

@ExtendWith(MockitoExtension.class)
class JenkinsServiceTest {
  @Mock
  private JenkinsRepository jenkinsRepository;
  @InjectMocks
  private JenkinsServiceImpl unitUnderTest;

  @Test
  void check_everythingStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void findByHeadSha_shouldReturnJobIfFound() {
    final String headSha = RandomStringUtils.randomAlphabetic(10);
    final JenkinsJob jenkinsJob = mock(JenkinsJob.class);

    when(jenkinsRepository.findFirstByHeadHash(headSha)).thenReturn(Optional.of(
        jenkinsJob));
    when(jenkinsJob.getHeadHash()).thenReturn(headSha);

    final Optional<JenkinsJob> foundJob = unitUnderTest.findOneByHeadSha(headSha);

    assertAll(
        () -> assertNotNull(foundJob),
        () -> assertTrue(foundJob.isPresent()),
        () -> assertEquals(headSha, foundJob.get().getHeadHash())
    );
  }

  @Test
  void findByHeadSha_shouldReturnEmptyIfNotFound() {
    when(jenkinsRepository.findFirstByHeadHash(anyString())).thenReturn(Optional.empty());

    final String headSha = RandomStringUtils.randomAlphabetic(10);

    final Optional<JenkinsJob> foundJob = unitUnderTest.findOneByHeadSha(headSha);

    assertAll(
        () -> assertNotNull(foundJob),
        () -> assertFalse(foundJob.isPresent())
    );
  }

  @Test
  void findByHeadSha_shouldCheckInputParameters() {
    assertThrows(NullPointerException.class, () -> {
      unitUnderTest.findOneByHeadSha(null);
    });
  }
}
