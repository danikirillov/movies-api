package com.dd.movies_api.controller;

import com.dd.movies_api.model.FilmOscarStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class IsBestPictureMovieControllerTest
{
  private static final String X_API_TOKEN = "X-API-Token";
  private static final String BASE_URL = "/is-best-picture-movie";
  @Autowired
  TestRestTemplate httpClient;

  @Test
  @DisplayName("isBestPictureMovie - with best picture movie, correct api-token - title + true")
  void isBestPictureMovieTestWithCorrectInputAndApiToken()
  {
    final var token = "42";
    final var title = "Black Swan";
    final var expectedResponse = new FilmOscarStatus(title, true);
    setSecurityHeader(token);

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
    setSecurityHeader(token);

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

  private void setSecurityHeader(String token)
  {
    httpClient.getRestTemplate().setInterceptors(
        Collections.singletonList((request, body, execution) ->
                                  {
                                    request.getHeaders()
                                           .add(X_API_TOKEN, token
                                           );
                                    return execution.execute(request, body);
                                  }));
  }

}