package com.dd.movies_api.service;

import com.dd.movies_api.dao.RatedMovieRepository;
import com.dd.movies_api.dao.RatingsRepository;
import com.dd.movies_api.model.RatedMovie;
import com.dd.movies_api.model.RatedMovieEntity;
import com.dd.movies_api.model.RatingsEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatedMovieService
{
  public static final int NO_BOX_OFFICE = -1;
  private static final double ONE_NUMBER_AFTER_DOT = 10.0d;
  private static final Sort BOX_OFFICE_SORT = Sort.by(Sort.Direction.DESC, "boxOffice");
  private static final Sort RATING_SORT = Sort.by(Sort.Direction.DESC, "rating");
  private static final PageRequest LIMIT_BY_TEN_AND_SORT_BY_BOX_OFFICE =
      PageRequest.of(0, 10, RATING_SORT.and(BOX_OFFICE_SORT));

  private final RatedMovieRepository ratedMovieRepository;
  private final RatingsRepository ratingsRepository;

  public RatedMovieService(RatedMovieRepository ratedMovieRepository, RatingsRepository ratingsRepository)
  {
    this.ratedMovieRepository = ratedMovieRepository;
    this.ratingsRepository = ratingsRepository;
  }

  public List<RatedMovie> getTopRatedMovies()
  {
    final var ratedMovieEntities = ratedMovieRepository.findAll(LIMIT_BY_TEN_AND_SORT_BY_BOX_OFFICE);

    final var result = new ArrayList<RatedMovie>();
    for (var ratedMovieEntity : ratedMovieEntities)
    {
      final var ratedMovie = new RatedMovie()
          .title(ratedMovieEntity.getTitle())
          .rating(formatRating(ratedMovieEntity.getRating()))
          .boxOffice(ratedMovieEntity.getBoxOffice());
      result.add(ratedMovie);
    }

    return result;
  }

  private String formatRating(int rating)
  {
    double dr = rating / 10.0d;
    return String.format("%.1f/5.0", dr);
  }

  /**
   * Method updates a user rating for this film and a total rating too.
   *
   * @param rating - One more rating in[0, 5]
   * @return - A boxOffice value of an updated movie or -1 if it was a first rating for this movie
   */
  @Transactional
  public long updateRating(String title, Integer rating, String apiKey)
  {
    int actualRatersAmount = 1;
    long actualRating = rating;

    // Fetching current user rating for this film title and changing it if necessary.
    RatingsEntity currentRating = ratingsRepository.findByTitleAndApiKey(title, apiKey);
    if (currentRating == null)
    {
      currentRating = new RatingsEntity();
      currentRating.setTitle(title);
      currentRating.setApiKey(apiKey);
    }
    else
    {
      actualRatersAmount = 0;
      actualRating -= currentRating.getRating();
    }
    currentRating.setRating(rating);
    ratingsRepository.save(currentRating);

    // Updating movie rating information
    RatedMovieEntity ratedMovie = ratedMovieRepository.findByTitle(title);

    if (ratedMovie == null)
    {
      ratedMovie = new RatedMovieEntity();
      ratedMovie.setTitle(title);
      ratedMovie.setBoxOffice(NO_BOX_OFFICE);
    }

    ratedMovie.setRatingTotal(ratedMovie.getRatingTotal() + actualRating);
    ratedMovie.setRatersAmount(ratedMovie.getRatersAmount() + actualRatersAmount);
    ratedMovie.setRating(computeRating(ratedMovie));

    ratedMovieRepository.save(ratedMovie);

    return ratedMovie.getBoxOffice();
  }

  private int computeRating(RatedMovieEntity ratedMovie)
  {
    // This zero thing can happen only due to some database inconsistency, but it's really annoying to have division by 0.
    if (ratedMovie.getRatersAmount() == 0)
    {
      return 0;
    }

    Double rating = ratedMovie.getRatingTotal() * ONE_NUMBER_AFTER_DOT / ratedMovie.getRatersAmount();
    return rating.intValue();
  }
}
