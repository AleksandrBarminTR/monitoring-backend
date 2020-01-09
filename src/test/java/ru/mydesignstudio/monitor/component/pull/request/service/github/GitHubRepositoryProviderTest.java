package ru.mydesignstudio.monitor.component.pull.request.service.github;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;

@ExtendWith(MockitoExtension.class)
class GitHubRepositoryProviderTest {
  private String repositoryName = RandomStringUtils.randomAlphabetic(20);

  @Mock
  private Repository repository;
  @Mock
  private GHRepository ghRepository;
  @Mock
  private GitHub gitHub;
  @InjectMocks
  private GitHubRepositoryProvider unitUnderTest;

  @BeforeEach
  void setUp() {
    when(repository.getRepositoryName()).thenReturn(repositoryName);
  }

  @Test
  void provide_returnsByName() throws Exception {
    when(gitHub.getRepository(repository.getRepositoryName())).thenReturn(ghRepository);

    final GHRepository provided = unitUnderTest.provide(repository);

    assertNotNull(provided);
  }

  @Test
  void provide_throwsAnException() throws Exception {
    when(gitHub.getRepository(anyString())).thenThrow(IOException.class);

    assertThrows(RuntimeException.class, () -> {
      unitUnderTest.provide(repository);
    });
  }
}