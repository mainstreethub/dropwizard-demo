package com.mainstreethub.project.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import static com.google.common.base.Preconditions.checkNotNull;
import org.apache.commons.codec.binary.Base64;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;


public class ProjectClientBuilder {

  private final RestAdapter restAdapter;

  /**
   * Creates a new ProjectClient.
   * @param endpoint resource URL
   * @param apiUser api username
   * @param apiSecret api secret key
   */
  public ProjectClientBuilder(String endpoint, String apiUser, String apiSecret) {

    checkNotNull(endpoint);
    checkNotNull(apiUser);
    checkNotNull(apiSecret);

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new Jdk8Module());
    mapper.registerModule(new ParameterNamesModule());

    restAdapter = new RestAdapter.Builder()
            .setEndpoint(endpoint)
            .setRequestInterceptor(new ApiKeyInterceptor(apiUser, apiSecret))
            .setConverter(new JacksonConverter(mapper))
            .build();
  }

  public ProjectClient newProjectClient() {
    return restAdapter.create(ProjectClient.class);
  }

  private static final class ApiKeyInterceptor implements RequestInterceptor {
    private final String authorization;

    ApiKeyInterceptor(String user, String secret) {
      checkNotNull(user);
      checkNotNull(secret);

      String token = Base64.encodeBase64String(String.format("%s:%s", user, secret).getBytes());
      authorization = "Basic " + token;
    }

    @Override
    public void intercept(RequestFacade request) {
      request.addHeader("Authorization", authorization);
    }
  }
}
