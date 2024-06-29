package com.dd.movies_api.config;

import com.dd.movies_api.util.CsvUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Set;

@Configuration
public class CsvConfiguration
{

  @Bean(name = "bestPictures")
  public Set<String> fetchBestPictures(@Value("${csv.path.best_pictures}") String filePath) throws IOException
  {
    return CsvUtil.fetchMovieTitles(filePath);
  }
}
