package ru.mydesignstudio.monitor.component.test.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import org.apache.commons.lang3.RandomStringUtils;

public class ObjectMother {
  public <T> T create(Class<T> targetClass) throws Exception {
    final Constructor<T> defaultConstructor = targetClass.getConstructor();
    if (defaultConstructor == null) {
      throw new IllegalArgumentException(String.format(
          "Class %s has no default constructor",
          targetClass.getName()
      ));
    }
    final T instance = defaultConstructor.newInstance();

    for (Field field : targetClass.getDeclaredFields()) {
      field.setAccessible(true);
      field.set(instance, createValue(field.getType()));
    }

    return instance;
  }

  private Object createValue(Class<?> fieldType) {
    if (String.class.isAssignableFrom(fieldType)) {
      return RandomStringUtils.randomAlphabetic(20);
    }
    throw new UnsupportedOperationException(String.format(
        "Unsupported field type %s",
        fieldType.getName()
    ));
  }
}
