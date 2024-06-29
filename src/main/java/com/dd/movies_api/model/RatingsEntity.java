package com.dd.movies_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "ratings")
public class RatingsEntity
{
  @Id
  private long id;
  private String title;
  private String apiKey;
  private int rating;

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getApiKey()
  {
    return apiKey;
  }

  public void setApiKey(String apiKey)
  {
    this.apiKey = apiKey;
  }

  public int getRating()
  {
    return rating;
  }

  public void setRating(int rating)
  {
    this.rating = rating;
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
    RatingsEntity that = (RatingsEntity) o;
    return rating == that.rating && Objects.equals(title, that.title) &&
        Objects.equals(apiKey, that.apiKey);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(title, apiKey);
  }
}
