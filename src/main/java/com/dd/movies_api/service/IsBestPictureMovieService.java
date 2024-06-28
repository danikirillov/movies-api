package com.dd.movies_api.service;

import com.dd.movies_api.model.FilmOscarStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class IsBestPictureMovieService
{
  private final Set<String> bestPictures;

  public IsBestPictureMovieService(Set<String> bestPictures)
  {
    this.bestPictures = bestPictures;
  }

  public FilmOscarStatus isBestPicture(String title)
  {
    return new FilmOscarStatus(
        title,
        bestPictures.contains(title)
    );
  }
}
