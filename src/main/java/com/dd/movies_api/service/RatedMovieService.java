package com.dd.movies_api.service;

import com.dd.movies_api.model.RatedMovie;
import com.dd.movies_api.repository.RatedMovieRepository;
import com.dd.movies_api.repository.RatingsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatedMovieService
{
  private static final Sort BOX_OFFICE_SORT = Sort.by(Sort.Direction.ASC, "boxOffice");
  private static final Sort RATING_SORT = Sort.by(Sort.Direction.ASC, "rating");
  private static final PageRequest LIMIT_BY_TEN_AND_SORT_BY_BOX_OFFICE =
      PageRequest.of(1, 10, RATING_SORT.and(BOX_OFFICE_SORT));

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
    var currentRating = ratingsRepository.findByTitleAndApiKey(title, apiKey);
//    if (currentRating.getId)
    return 1;
  }
}
