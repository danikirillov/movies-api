package com.dd.movies_api.dao;

import com.dd.movies_api.model.BoxOfficeProjection;
import com.dd.movies_api.model.MoviesApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OmdbGateway
{
  private final RestClient restClient;
  @Value("${omdb.apiKey}")
  private String apiKey;
  @Value("${omdb.url}")
  private String omdbUrl;

  public OmdbGateway(RestClient restClient)
  {
    this.restClient = restClient;
  }

  public String getBoxOffice(String title)
  {
    final var boxOffice =
        restClient
            .get()
            .uri(createUri(title))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(BoxOfficeProjection.class);

    validateBoxOffice(boxOffice, title);

    return boxOffice.getBoxOffice();
  }

  private void validateBoxOffice(BoxOfficeProjection boxOffice, String title)
  {
    if (boxOffice == null || boxOffice.getBoxOffice() == null || boxOffice.getBoxOffice().isBlank())
    {
      throw new MoviesApiException(String.format("Box office is invalid. Probably film \"%s\" doesn't exist.", title));
    }
  }

  private String createUri(String title)
  {
    return String.format("%s/?t=\"%s\"&apikey=%s", omdbUrl, title, apiKey);
  }
}
