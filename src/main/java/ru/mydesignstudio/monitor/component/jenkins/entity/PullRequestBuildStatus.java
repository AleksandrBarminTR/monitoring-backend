package ru.mydesignstudio.monitor.component.jenkins.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import ru.mydesignstudio.monitor.component.pull.request.model.BuildStatus;

@Entity
@Table(name = "PULL_REQUEST_BUILD_STATUSES")
public class PullRequestBuildStatus {
  @Id
  @Column(name = "BUILD_STATUS_ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "BUILD_ID")
  private PullRequestBuilds builds;

  @Column(name = "BUILD_STATUS_HEAD_HEASH")
  private String headHash;

  @Column(name = "BUILD_STATUS")
  private BuildStatus status;

  @Column(name = "BUILD_URL")
  private String jobUrl;
}
