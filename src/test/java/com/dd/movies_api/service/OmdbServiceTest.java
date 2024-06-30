package com.dd.movies_api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class OmdbServiceTest
{
  @Test
  @DisplayName("formatBoxOffice - with valid input - valid long")
  void formatBoxOfficeTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
  {
    final var boxOffice = "$106,954,678,000,000,000";
    final long expected = 106_954_678_000_000_000L;
    final var omdbService = new OmdbService(null, null);
    final var formatBoxOfficeMethod = getFboMethod();

    final long actual = (long) formatBoxOfficeMethod.invoke(omdbService, boxOffice, null);

    Assertions.assertEquals(expected, actual);
  }

  private Method getFboMethod() throws NoSuchMethodException
  {
    final var m = OmdbService.class.getDeclaredMethod("formatBoxOffice", String.class, String.class);
    m.setAccessible(true);
    return m;
  }

}