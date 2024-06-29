package com.dd.movies_api.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;

public class ApiKeyAuth extends AbstractAuthenticationToken
{
  private final String apiKey;

  public ApiKeyAuth(String apiKey, Collection<? extends GrantedAuthority> authorities)
  {
    super(authorities);
    this.apiKey = apiKey;
    setAuthenticated(true);
  }

  @Override
  public Object getCredentials()
  {
    return null;
  }

  @Override
  public Object getPrincipal()
  {
    return apiKey;
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
    if (!super.equals(o))
    {
      return false;
    }
    ApiKeyAuth that = (ApiKeyAuth) o;
    return Objects.equals(apiKey, that.apiKey);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(super.hashCode(), apiKey);
  }
}
