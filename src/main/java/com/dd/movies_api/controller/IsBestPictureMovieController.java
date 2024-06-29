package com.dd.movies_api.controller;

import com.dd.movies_api.api.IsBestPictureMovieApi;
import com.dd.movies_api.model.FilmOscarStatus;
import com.dd.movies_api.service.IsBestPictureMovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IsBestPictureMovieController implements IsBestPictureMovieApi
{
  private static final Logger LOG = LoggerFactory.getLogger(IsBestPictureMovieController.class);
  private final IsBestPictureMovieService service;

  public IsBestPictureMovieController(IsBestPictureMovieService service)
  {
    this.service = service;
  }

  @Override
  public ResponseEntity<FilmOscarStatus> isBestPictureMovie(String title, String apiKey)
  {
    LOG.info("Checking a movie with a title = '{}' for having a Best Picture Oscar. API-KEY: {}", title, apiKey);
    return ResponseEntity.ok(service.isBestPicture(title));
  }
}
