package com.dd.movies_api.service;

import com.dd.movies_api.model.RatedMovie;
import com.dd.movies_api.model.RatedMovieEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class RatedMovieServiceTest
{

  @Test
  @DisplayName("formatRating - with valid input - nice formatted string")
  void formatRatingTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
  {
    int rating = 25;
    final var expected = "2.5/5.0";
    final var ratedMovieService = new RatedMovieService(null, null);
    final var computeRatingMethod = getFrMethod();

    final String actual = (String) computeRatingMethod.invoke(ratedMovieService, rating);

    Assertions.assertEquals(expected, actual);
  }

  private Method getFrMethod() throws NoSuchMethodException
  {
    final var m = RatedMovieService.class.getDeclaredMethod("formatRating", int.class);
    m.setAccessible(true);
    return m;
  }

  @Test
  @DisplayName("computeRating - with valid input - nice formatted string")
  void computeRatingTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
  {
    final var ratedMovie = new RatedMovieEntity();
    ratedMovie.setRatingTotal(15);
    ratedMovie.setRatersAmount(6);
    final int expected = 25;
    final var ratedMovieService = new RatedMovieService(null, null);
    final var computeRatingMethod = getCrMethod();

    final var actual = (Integer) computeRatingMethod.invoke(ratedMovieService, ratedMovie);

    Assertions.assertEquals(expected, actual);
  }


  private Method getCrMethod() throws NoSuchMethodException
  {
    final var m = RatedMovieService.class.getDeclaredMethod("computeRating", RatedMovieEntity.class);
    m.setAccessible(true);
    return m;
  }

}