package com.dd.movies_api.model;

public class MoviesApiException extends RuntimeException
{
  public MoviesApiException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public MoviesApiException(String message)
  {
    super(message);
  }
}
