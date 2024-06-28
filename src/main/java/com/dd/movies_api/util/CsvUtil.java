package com.dd.movies_api.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public final class CsvUtil
{
  private static final int AWARD_NAME_POSITION = 1;
  private static final int TITLE_POSITION = 2;
  private static final String BEST_PICTURE = "Best Picture";

  private CsvUtil()
  {
  }

  public static Set<String> fetchMovieTitles(String filePath) throws IOException
  {
    final var path = createPath(filePath);
    try (final var lines = Files.lines(path))
    {
      final var titles = new HashSet<String>();
      lines
          .map(movieInfo -> movieInfo.split(","))
          .filter(CsvUtil::isLineCorrect)
          .filter(CsvUtil::isRealBestPictureMovie)
          .map(movieInfo -> movieInfo[TITLE_POSITION])
          .forEach(titles::add);

      return titles;
    }
  }

  private static boolean isRealBestPictureMovie(String[] moviesInfo)
  {
    return BEST_PICTURE.equals(moviesInfo[AWARD_NAME_POSITION]);
  }

  private static boolean isLineCorrect(String[] movieInfo)
  {
    return movieInfo.length > TITLE_POSITION;
  }

  private static Path createPath(String filePath) throws IOException
  {
    final var uri = new ClassPathResource(filePath).getURI();
    return Path.of(uri);
  }
}
