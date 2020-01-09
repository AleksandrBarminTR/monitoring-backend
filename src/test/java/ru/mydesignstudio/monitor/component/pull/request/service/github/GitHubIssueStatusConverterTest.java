package ru.mydesignstudio.monitor.component.pull.request.service.github;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;
import lombok.Value;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.kohsuke.github.GHIssueState;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.monitor.component.pull.request.model.GitHubStatus;

@ExtendWith(MockitoExtension.class)
class GitHubIssueStatusConverterTest {
  @InjectMocks
  private GitHubIssueStatusConverter unitUnderTest;

  public static Stream<TestItem> testItems() {
    return Stream.of(
        new TestItem(Collections.emptySet(), GHIssueState.ALL),
        new TestItem(Sets.immutableEnumSet(GitHubStatus.OPEN), GHIssueState.OPEN),
        new TestItem(Sets.immutableEnumSet(GitHubStatus.OPEN, GitHubStatus.CLOSED), GHIssueState.ALL),
        new TestItem(Sets.immutableEnumSet(GitHubStatus.CLOSED), GHIssueState.CLOSED)
    );
  }

  @ParameterizedTest
  @MethodSource("testItems")
  void convert_emptyToAll(TestItem item) {
    final GHIssueState value = unitUnderTest.convert(item.statutes);

    assertEquals(item.state, value);
  }

  @Value
  static class TestItem {
    private final Set<GitHubStatus> statutes;
    private final GHIssueState state;
  }
}