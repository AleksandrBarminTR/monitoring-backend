package ru.mydesignstudio.monitor.component.jenkins.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJob;
import ru.mydesignstudio.monitor.component.jenkins.entity.JenkinsJobStatus;

@Repository
public interface JenkinsRepository extends JpaRepository<JenkinsJob, Long> {
  Optional<JenkinsJob> findFirstByHeadHash(String headHash);

  List<JenkinsJob> findAllByStatus(JenkinsJobStatus status);
}
