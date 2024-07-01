package com.dd.movies_api.controller;

import com.dd.movies_api.HttpClientUtil;
import com.dd.movies_api.model.FilmOscarStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles({"test"})
class IsBestPictureMovieControllerTest
{
  private static final String BASE_URL = "/is-best-picture-movie";
  @Autowired
  TestRestTemplate httpClient;
  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @BeforeAll
  static void beforeAll()
  {
    postgres.start();
  }

  @AfterAll
  static void afterAll()
  {
    postgres.stop();
  }


  @Test
  @DisplayName("isBestPictureMovie - with best picture movie, correct api-token - title + true")
  void isBestPictureMovieTestWithCorrectInputAndApiToken()
  {
    final var token = "42";
    final var title = "Black Swan";
    final var expectedResponse = new FilmOscarStatus(title, true);
    HttpClientUtil.setSecurityHeader(httpClient, token);

    final var actualResponseEntity =
        httpClient
            .getForEntity(
                createUrlForTitle(title),
                FilmOscarStatus.class
            );

    Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
    Assertions.assertEquals(expectedResponse, actualResponseEntity.getBody());
  }

  @Test
  @DisplayName("isBestPictureMovie - with regular movie, correct api-token - title + false")
  void isBestPictureMovieTestWithJustAMovieAndApiToken()
  {
    final var token = "42";
    final var title = "Shrek";
    final var expectedResponse = new FilmOscarStatus(title, false);
    HttpClientUtil.setSecurityHeader(httpClient, token);

    final var actualResponseEntity =
        httpClient
            .getForEntity(
                createUrlForTitle(title),
                FilmOscarStatus.class
            );

    Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
    Assertions.assertEquals(expectedResponse, actualResponseEntity.getBody());
  }

  @Test
  @DisplayName("isBestPictureMovie - with regular movie, no api-token - unauthorized ex")
  void isBestPictureMovieTestWithJustAMovieAndNoApiToken()
  {
    final var title = "Shrek";

    final var actualResponseEntity =
        httpClient
            .getForEntity(
                createUrlForTitle(title),
                FilmOscarStatus.class
            );

    Assertions.assertEquals(HttpStatus.UNAUTHORIZED, actualResponseEntity.getStatusCode());
  }

  private String createUrlForTitle(String title)
  {
    return String.format("%s?title=%s", BASE_URL, title);
  }

}