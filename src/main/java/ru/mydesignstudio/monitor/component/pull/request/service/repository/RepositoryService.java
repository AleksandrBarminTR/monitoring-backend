package ru.mydesignstudio.monitor.component.pull.request.service.repository;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;

public interface RepositoryService {
  List<Repository> findAll();

  Optional<Repository> findByUrl(URL url);
}
