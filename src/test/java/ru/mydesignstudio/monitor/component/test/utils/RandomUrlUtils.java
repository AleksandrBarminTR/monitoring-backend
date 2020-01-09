package ru.mydesignstudio.monitor.component.test.utils;

import java.net.URL;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomUrlUtils {
  public String randomUrlString(int length) {
    return "http://" + RandomStringUtils.randomAlphabetic(length) + ".com";
  }

  @SneakyThrows
  public URL randomUrl(int length) {
    return new URL(randomUrlString(length));
  }
}
