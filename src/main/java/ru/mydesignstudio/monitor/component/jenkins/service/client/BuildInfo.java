package ru.mydesignstudio.monitor.component.jenkins.service.client;

import java.net.URL;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "workflowRun")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
@XmlAccessorType(XmlAccessType.FIELD)
public class BuildInfo {
  private String result;
  private URL url;
  private int buildNumber;

  public BuildInfo() {
  }

  public BuildInfo(String result) {
    this.result = result;
  }

  public BuildInfo(String result, int buildNumber) {
    this.result = result;
    this.buildNumber = buildNumber;
  }
}
