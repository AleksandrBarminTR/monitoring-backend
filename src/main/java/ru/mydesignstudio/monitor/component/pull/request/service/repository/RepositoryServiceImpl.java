package ru.mydesignstudio.monitor.component.pull.request.service.repository;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService {
  private final Gson gson;
  private final RepositoryFactory repositoryFactory;

  @Value("classpath:/repositories.json")
  private Resource repositoryList;
  private List<Repository> repositories;

  @PostConstruct
  public void init() {
    try (final Reader reader = new InputStreamReader(repositoryList.getInputStream())) {
      final Map<String, String>[] descriptions = gson.fromJson(reader, Map[].class);
      repositories = Arrays.stream(descriptions)
          .map(description -> repositoryFactory.create(description.get("repositoryUrl"), description.get("jenkinsUrl")))
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Repository> findAll() {
    return repositories;
  }
}
