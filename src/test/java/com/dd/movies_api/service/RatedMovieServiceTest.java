package com.dd.movies_api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class RatedMovieServiceTest
{

  @Test
  @DisplayName("computeRating - with valid input - nice formatted string")
  void computeRatingTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
  {
    int rating = 25;
    final var expected = "2.5/5.0";
    final var ratedMovieService = new RatedMovieService(null, null);
    final var computeRatingMethod = getCrMethod();

    final String actual = (String) computeRatingMethod.invoke(ratedMovieService, rating);

    Assertions.assertEquals(expected, actual);
  }

  private Method getCrMethod() throws NoSuchMethodException
  {
    final var m = RatedMovieService.class.getDeclaredMethod("formatRating", int.class);
    m.setAccessible(true);
    return m;
  }

}