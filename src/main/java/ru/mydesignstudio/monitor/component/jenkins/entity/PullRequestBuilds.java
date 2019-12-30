package ru.mydesignstudio.monitor.component.jenkins.entity;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

@Data
@Entity
@Table(name = "PULL_REQUESTS")
public class PullRequestBuilds {
  @Id
  @NotNull
  @Column(name = "PULL_REQUEST_ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  @Column(name = "PULL_REQUEST_URL")
  private URL pullRequestUrl;

  @NotNull
  @Column(name = "PULL_REQUEST_CREATED")
  @CreatedDate
  private LocalDateTime created;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "builds")
  private List<PullRequestBuildStatus> builds = new ArrayList<>();
}
