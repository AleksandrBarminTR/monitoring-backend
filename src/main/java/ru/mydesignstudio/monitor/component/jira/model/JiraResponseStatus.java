package ru.mydesignstudio.monitor.component.jira.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class JiraResponseStatus {
  @SerializedName("self")
  private @NonNull String key;
  private @NonNull String description;
  private @NonNull String name;
}
