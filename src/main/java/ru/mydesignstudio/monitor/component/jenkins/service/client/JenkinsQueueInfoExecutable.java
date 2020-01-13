package ru.mydesignstudio.monitor.component.jenkins.service.client;

import java.net.URL;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
@Data
public class JenkinsQueueInfoExecutable {
  @XmlElement(name = "number")
  private Integer number;
  @XmlElement(name = "url")
  private URL url;
}
