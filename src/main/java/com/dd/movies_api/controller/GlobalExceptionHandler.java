package com.dd.movies_api.controller;

import com.dd.movies_api.model.Error;
import com.dd.movies_api.model.MoviesApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.UUID;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  private <T extends Exception> Error createError(final T ex)
  {
    return new Error()
        .id("EID-".concat(UUID.randomUUID().toString()))
        .message(ex.getMessage());
  }

  @ExceptionHandler(MoviesApiException.class)
  protected void handleMoviesApiException(final MoviesApiException ex)
  {
    final var error = createError(ex);
    LOG.error(error.toString(), ex);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request)
  {
    final var error = createError(ex);
    LOG.error(error.toString(), ex);
    return new ResponseEntity<>(error, headers, HttpStatus.BAD_REQUEST);
  }
}
