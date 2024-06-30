package com.dd.movies_api.config;

import com.dd.movies_api.model.ApiKeyAuth;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ApiKeyFilter extends OncePerRequestFilter
{
  public static final String API_KEY_HEADER = "X-API-Key";
  private final String validKey;

  public ApiKeyFilter(String validKey)
  {
    this.validKey = validKey;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException
  {
    final var apiKey = request.getHeader(API_KEY_HEADER);
    if (apiKey == null || isApiKeyInvalid(apiKey))
    {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
    else
    {
      final var authentication = new ApiKeyAuth(apiKey, AuthorityUtils.NO_AUTHORITIES);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

  private boolean isApiKeyInvalid(String apiKey)
  {
    return !apiKey.endsWith(validKey);
  }

}
