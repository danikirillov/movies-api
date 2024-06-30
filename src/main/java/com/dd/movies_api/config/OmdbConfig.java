package com.dd.movies_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class OmdbConfig
{
  @Bean
  public RestClient omdbRestClient()
  {
    return RestClient.create();
  }
}
