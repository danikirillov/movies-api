package com.dd.movies_api.controller;

import com.dd.movies_api.api.RatedMoviesApi;
import com.dd.movies_api.model.RatedMovie;
import com.dd.movies_api.model.UpdateMovieRatingRequest;
import com.dd.movies_api.service.RatedMovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RatedMoviesController implements RatedMoviesApi
{
  private static final Logger LOG = LoggerFactory.getLogger(RatedMoviesController.class);
  private final RatedMovieService ratedMovieService;

  public RatedMoviesController(RatedMovieService ratedMovieService)
  {
    this.ratedMovieService = ratedMovieService;
  }

  @Override
  public ResponseEntity<List<RatedMovie>> getTopRatedMovies(String apiKey)
  {
    LOG.info("Get top 10 for api-key {}", apiKey);
    return ResponseEntity.ok(ratedMovieService.getTopRatedMovies());
  }

  @Override
  public ResponseEntity<String> updateMovieRating(String apiKey, UpdateMovieRatingRequest updateMovieRatingRequest)
  {
    LOG.info("Update movie rating with {} for api-key {}", updateMovieRatingRequest, apiKey);

    final long boxOffice = ratedMovieService.updateRating(
        updateMovieRatingRequest.getTitle(),
        updateMovieRatingRequest.getRating(),
        apiKey
    );

    return ResponseEntity.ok("Rating submitted.");
  }
}
