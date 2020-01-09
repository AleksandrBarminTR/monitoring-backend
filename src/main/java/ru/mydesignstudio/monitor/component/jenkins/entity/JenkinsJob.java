package ru.mydesignstudio.monitor.component.jenkins.entity;

import java.net.URL;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "JENKINS_JOBS")
public class JenkinsJob {
  @Id
  @Column(name = "JOB_ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "JOB_CREATED")
  private LocalDateTime created;

  @Column(name = "JOB_STATUS")
  private JenkinsJobStatus status;

  @Column(name = "JOB_MERGE_REQUEST")
  private URL mergeRequest;

  @Column(name = "JOB_URL")
  private URL jobUrl;

  @Column(name = "JOB_HEAD_HASH")
  private String headHash;
}
