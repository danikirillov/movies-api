package com.dd.movies_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BoxOfficeProjection
{
  private String boxOffice;

  @JsonProperty("BoxOffice")
  public String getBoxOffice()
  {
    return boxOffice;
  }

  public void setBoxOffice(String boxOffice)
  {
    this.boxOffice = boxOffice;
  }
}
