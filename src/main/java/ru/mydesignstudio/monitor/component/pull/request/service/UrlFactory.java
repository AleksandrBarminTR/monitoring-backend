package ru.mydesignstudio.monitor.component.pull.request.service;

import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.stereotype.Component;

@Component
public class UrlFactory {
  public URL create(String url) {
    try {
      return new URL(url);
    } catch (MalformedURLException e) {
      throw new RuntimeException("Malformed URL", e);
    }
  }
}
