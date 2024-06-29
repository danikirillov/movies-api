package com.dd.movies_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "rated_movie")
public class RatedMovieEntity
{
  private int id;
  private int rating;
  private long ratingTotal;
  private long ratersAmount;
  private String title;
  private long boxOffice;

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public int getRating()
  {
    return rating;
  }

  public void setRating(int rating)
  {
    this.rating = rating;
  }

  public long getRatingTotal()
  {
    return ratingTotal;
  }

  public void setRatingTotal(long ratingTotal)
  {
    this.ratingTotal = ratingTotal;
  }

  public long getRatersAmount()
  {
    return ratersAmount;
  }

  public void setRatersAmount(long ratersAmount)
  {
    this.ratersAmount = ratersAmount;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public long getBoxOffice()
  {
    return boxOffice;
  }

  public void setBoxOffice(long boxOffice)
  {
    this.boxOffice = boxOffice;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }
    RatedMovieEntity that = (RatedMovieEntity) o;
    return id == that.id && ratingTotal == that.ratingTotal && ratersAmount == that.ratersAmount &&
        boxOffice == that.boxOffice && Objects.equals(title, that.title);
  }

  @Override
  public int hashCode()
  {
    return id;
  }

  @Override
  public String toString()
  {
    return "RatedMovieEntity{" +
        "id=" + id +
        ", rating=" + rating +
        ", ratingTotal=" + ratingTotal +
        ", ratersAmount=" + ratersAmount +
        ", title='" + title + '\'' +
        ", boxOffice=" + boxOffice +
        '}';
  }
}
