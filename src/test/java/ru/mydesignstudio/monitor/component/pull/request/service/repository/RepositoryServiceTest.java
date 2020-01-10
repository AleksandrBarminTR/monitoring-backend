package ru.mydesignstudio.monitor.component.pull.request.service.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;
import ru.mydesignstudio.monitor.component.test.utils.RandomUrlUtils;

@ExtendWith(MockitoExtension.class)
class RepositoryServiceTest {
  @InjectMocks
  private RandomUrlUtils randomUrlUtils;
  @InjectMocks
  private RepositoryServiceImpl unitUnderTest;

  @Test
  void check_everythingStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void findByUrl_shouldReturnRepositoryIfExists() {
    final Repository repository = mock(Repository.class);
    final URL url = randomUrlUtils.randomUrl(10);
    ReflectionTestUtils.setField(unitUnderTest, "repositories", Collections.singletonList(repository));
    when(repository.getRepositoryUrl()).thenReturn(url);

    final Optional<Repository> foundRepository = unitUnderTest.findByUrl(url);

    assertAll(
        () -> assertNotNull(foundRepository),
        () -> assertTrue(foundRepository.isPresent())
    );
  }

  @Test
  void findByUrl_shouldCheckInputParameters() {
    assertThrows(NullPointerException.class, () -> unitUnderTest.findByUrl(null));
  }
}