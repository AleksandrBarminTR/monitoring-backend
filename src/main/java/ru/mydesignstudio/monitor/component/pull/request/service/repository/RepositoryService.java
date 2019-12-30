package ru.mydesignstudio.monitor.component.pull.request.service.repository;

import java.util.List;
import ru.mydesignstudio.monitor.component.pull.request.model.Repository;

public interface RepositoryService {
  List<Repository> findAll();
}
