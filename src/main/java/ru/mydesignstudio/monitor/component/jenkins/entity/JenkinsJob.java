package ru.mydesignstudio.monitor.component.jenkins.entity;

import java.net.URL;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "JENKINS_JOBS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JenkinsJob {
  @Id
  @Column(name = "JOB_ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "JOB_CREATED")
  private LocalDateTime created;

  @Column(name = "JOB_STATUS")
  private JenkinsJobStatus status;

  @Column(name = "JOB_PULL_REQUEST")
  private URL pullRequest;

  @Column(name = "JOB_FOLDER")
  private String jobFolder;

  @Column(name = "JOB_NAME")
  private String jobName;

  @Column(name = "JOB_BUILD_NUMBER")
  private int buildNumber;

  @Column(name = "JOB_BUILD_URL")
  private URL buildUrl;

  @Column(name = "JOB_HEAD_HASH")
  private String headHash;
}
