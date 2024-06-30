package com.dd.movies_api.controller;

import com.dd.movies_api.HttpClientUtil;
import com.dd.movies_api.dao.OmdbGateway;
import com.dd.movies_api.dao.RatedMovieRepository;
import com.dd.movies_api.model.RatedMovie;
import com.dd.movies_api.model.RatedMovieEntity;
import com.dd.movies_api.model.UpdateMovieRatingRequest;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles({"test"})
class RatedMoviesControllerTest
{
  private static final String BASE_URL = "/rated-movies";

  @Autowired
  TestRestTemplate httpClient;
  @MockBean
  OmdbGateway omdbGatewayMock;

  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
      "postgres:16-alpine"
  );
  @Autowired
  private RatedMovieRepository ratedMovieRepository;

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

  @BeforeEach
  void setUp()
  {
    ratedMovieRepository.deleteAll();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry)
  {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Test
  @DisplayName("getTopRatedMovies - correct api-token, more than 10 entries in db - 10 correctly sorted entries")
  void getTopRatedMoviesCorrectOutputTest()
  {
    final var token = "42";
    final var expectedResponseBody = createTestTopTen();
    fillTestDataForTopTenToDb();

    HttpClientUtil.setSecurityHeader(httpClient, token);

    final var actualResponseEntity =
        httpClient
            .getForEntity(
                BASE_URL,
                RatedMovie[].class
            );
    final var actualResponseBody = actualResponseEntity.getBody();

    Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
    Assertions.assertArrayEquals(expectedResponseBody, actualResponseBody);
  }

  private void fillTestDataForTopTenToDb()
  {

    final var testEntities = new ArrayList<RatedMovieEntity>();
    testEntities.add(createTestRME("A", 100_000L, 50, 1, 5));
    testEntities.add(createTestRME("B", 40_000L, 50, 1, 5));
    testEntities.add(createTestRME("C", 39_999L, 50, 1, 5));
    testEntities.add(createTestRME("D", 100_000L, 40, 1, 4));
    testEntities.add(createTestRME("E", 60_000L, 39, 1, 3));
    testEntities.add(createTestRME("F", 40_000L, 39, 1, 3));
    testEntities.add(createTestRME("G", 10L, 36, 1, 3));
    testEntities.add(createTestRME("H", 10L, 35, 1, 3));
    testEntities.add(createTestRME("I", 10L, 20, 1, 2));
    testEntities.add(createTestRME("K", 10L, 10, 1, 1));
    testEntities.add(createTestRME("NOT_GOOD0", 9L, 5, 10, 5));
    testEntities.add(createTestRME("NOT_GOOD1", 8L, 5, 10, 5));
    testEntities.add(createTestRME("NOT_GOOD2", 8L, 5, 10, 5));
    testEntities.add(createTestRME("NOT_GOOD3", 8L, 5, 10, 5));
    testEntities.add(createTestRME("NOT_GOOD4", 8L, 5, 10, 5));
    testEntities.add(createTestRME("NOT_GOOD5", 8L, 5, 10, 5));
    testEntities.add(createTestRME("NOT_GOOD6", 8L, 5, 10, 5));
    testEntities.add(createTestRME("NOT_GOOD7", 8L, 5, 10, 5));
    ratedMovieRepository.saveAll(testEntities);

  }

  private RatedMovieEntity createTestRME(String title, long boxOffice, int rating, long ratersAmount, long ratingTotal)
  {
    final var result = new RatedMovieEntity();
    result.setBoxOffice(boxOffice);
    result.setTitle(title);
    result.setRating(rating);
    result.setRatersAmount(ratersAmount);
    result.setRatingTotal(ratingTotal);
    return result;
  }

  private RatedMovie[] createTestTopTen()
  {
    final var result = new RatedMovie[10];
    result[0] = new RatedMovie().rating("5.0/5.0").title("A").boxOffice(100_000L);
    result[1] = new RatedMovie().rating("5.0/5.0").title("B").boxOffice(40_000L);
    result[2] = new RatedMovie().rating("5.0/5.0").title("C").boxOffice(39_999L);
    result[3] = new RatedMovie().rating("4.0/5.0").title("D").boxOffice(100_000L);
    result[4] = new RatedMovie().rating("3.9/5.0").title("E").boxOffice(60_000L);
    result[5] = new RatedMovie().rating("3.9/5.0").title("F").boxOffice(40_000L);
    result[6] = new RatedMovie().rating("3.6/5.0").title("G").boxOffice(10L);
    result[7] = new RatedMovie().rating("3.5/5.0").title("H").boxOffice(10L);
    result[8] = new RatedMovie().rating("2.0/5.0").title("I").boxOffice(10L);
    result[9] = new RatedMovie().rating("1.0/5.0").title("K").boxOffice(10L);
    return result;
  }

  @Test
  @DisplayName("updateMovieRating - correct request body and api-token - rating is submitted")
  void isBestPictureMovieTestWithCorrectInputAndApiToken() throws InterruptedException
  {
    Mockito
        .when(omdbGatewayMock.getBoxOffice("A"))
        .thenReturn("$100,000");

    HttpClientUtil.setSecurityHeader(httpClient, "42");

    var updateMovieRatingRequest = new UpdateMovieRatingRequest().rating(3).title("A");
    submitTestRating(updateMovieRatingRequest);

    // Let's wait a bit, due to asynchronous api call
    Thread.sleep(5000);

    //Check that rating was submitted in rated movies table
    final var expectedRatedMovies = new RatedMovie[1];
    expectedRatedMovies[0] = new RatedMovie().rating("3.0/5.0").title("A").boxOffice(100_000L);

    checkRating(expectedRatedMovies);

    // And for ensure correct rating computation, let's submit one more rating
    HttpClientUtil.setSecurityHeader(httpClient, "142");

    updateMovieRatingRequest = new UpdateMovieRatingRequest().rating(4).title("A");
    submitTestRating(updateMovieRatingRequest);

    // Check rating is computed well
    expectedRatedMovies[0] = new RatedMovie().rating("3.5/5.0").title("A").boxOffice(100_000L);
    checkRating(expectedRatedMovies);

  }

  private void checkRating(RatedMovie[] expectedRatedMovies)
  {
    final var actualRatedMovieEntity =
        httpClient
            .getForEntity(
                BASE_URL,
                RatedMovie[].class
            );

    Assertions.assertArrayEquals(expectedRatedMovies, actualRatedMovieEntity.getBody());
  }

  private void submitTestRating(UpdateMovieRatingRequest updateMovieRatingRequest)
  {
    final var actualResponseEntity =
        httpClient
            .postForEntity(
                BASE_URL,
                updateMovieRatingRequest,
                String.class
            );
    final var actualResponseBody = actualResponseEntity.getBody();

    Assertions.assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
    Assertions.assertEquals("Rating submitted.", actualResponseBody);
  }
}