package com.dd.movies_api.service;

import com.dd.movies_api.model.MoviesApiException;
import com.dd.movies_api.model.RatedMovieEntity;
import com.dd.movies_api.dao.OmdbGateway;
import com.dd.movies_api.dao.RatedMovieRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Service
public class OmdbService
{
  private final RatedMovieRepository ratedMovieRepository;
  private final OmdbGateway omdbGateway;

  public OmdbService(RatedMovieRepository ratedMovieRepository, OmdbGateway omdbGateway)
  {
    this.ratedMovieRepository = ratedMovieRepository;
    this.omdbGateway = omdbGateway;
  }

  @Async
  @Transactional
  public void updateBoxOffice(String title)
  {
    String boxOffice = omdbGateway.getBoxOffice(title);

    RatedMovieEntity ratedMovie = ratedMovieRepository.findByTitle(title);
    ratedMovie.setBoxOffice(formatBoxOffice(boxOffice, title));

    ratedMovieRepository.save(ratedMovie);
  }

  private long formatBoxOffice(String boxOffice, String title)
  {
    try
    {
      final var formatter = NumberFormat.getCurrencyInstance(Locale.US);
      final Number boxOfficeN = formatter.parse(boxOffice);
      return boxOfficeN.longValue();
    }
    catch (ParseException e)
    {
      throw new MoviesApiException(
          String.format("Error in boxOffice formatting for movie with title: %s", title),
          e
      );
    }
  }
}
