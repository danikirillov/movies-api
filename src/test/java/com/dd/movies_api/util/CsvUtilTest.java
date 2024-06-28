package com.dd.movies_api.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CsvUtilTest
{

  @Test
  @DisplayName("fetchMovieTitles - with valid input - a set of titles")
  void fetchMovieTitlesTestWithValidInput() throws IOException
  {
    final var filePath = "csv_test_data/best_pictures_good_data.csv";
    final var expectedTitles = new HashSet<String>();
    expectedTitles.add("Black Swan");
    expectedTitles.add("The Fighter");
    expectedTitles.add("Inception");
    expectedTitles.add("The Kids Are All Right");

    final var actualTitles = CsvUtil.fetchMovieTitles(filePath);

    Assertions.assertEquals(expectedTitles, actualTitles);
  }
}