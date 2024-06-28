package com.dd.movies_api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class MoviesApiApplicationTests
{

  @Test
  void contextLoads()
  {
    Assertions.assertTrue(true);
  }

}
