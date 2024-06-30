package com.dd.movies_api;

import com.dd.movies_api.config.ApiKeyFilter;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Collections;

public final class HttpClientUtil
{

  public static void setSecurityHeader(TestRestTemplate httpClient, String token)
  {
    httpClient.getRestTemplate().setInterceptors(
        Collections.singletonList((request, body, execution) ->
                                  {
                                    request.getHeaders()
                                           .add(ApiKeyFilter.API_KEY_HEADER, token
                                           );
                                    return execution.execute(request, body);
                                  }));
  }
}
