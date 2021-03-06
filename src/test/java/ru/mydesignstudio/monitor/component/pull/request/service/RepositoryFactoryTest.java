package ru.mydesignstudio.monitor.component.pull.request.service;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;
import ru.mydesignstudio.monitor.component.pull.request.service.github.UrlFactory;
import ru.mydesignstudio.monitor.component.pull.request.service.repository.RepositoryFactory;

@ExtendWith(MockitoExtension.class)
class RepositoryFactoryTest {
  @Spy
  private UrlFactory urlFactory;
  @InjectMocks
  private RepositoryFactory unitUnderTest;

  @BeforeEach
  void setUp() {
    unitUnderTest.init();
  }

  @Test
  void create_parsesUrlWithoutDotGit() {
    final Repository repository = unitUnderTest.create(
        "https://github.com/aabarmin/epam-dsc-2019",
        "http://ukmy-dvm-53.tlr.thomson.com:8080/job/BUILD/job/COMMON/job/ELLIS-PARENT/job/BUILD-MR/");

    assertAll(
        () -> assertNotNull(repository),
        () -> assertEquals(new URL("https://github.com/aabarmin/epam-dsc-2019"), repository.getRepositoryUrl()),
        () -> assertEquals("aabarmin/epam-dsc-2019", repository.getRepositoryName())
    );
  }

  @Test
  void create_parsesUrlWithDotGit() {
    final Repository repository = unitUnderTest.create(
        "https://github.com/aabarmin/epam-dsc-2019.git",
        "http://ukmy-dvm-53.tlr.thomson.com:8080/job/BUILD/job/COMMON/job/ELLIS-PARENT/job/BUILD-MR/"
    );

    assertAll(
        () -> assertNotNull(repository),
        () -> assertEquals(new URL("https://github.com/aabarmin/epam-dsc-2019.git"), repository.getRepositoryUrl()),
        () -> assertEquals("aabarmin/epam-dsc-2019", repository.getRepositoryName())
    );
  }
}
