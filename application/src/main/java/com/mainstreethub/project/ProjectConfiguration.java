package com.mainstreethub.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.bundles.apikey.ApiKeyBundleConfiguration;
import io.dropwizard.bundles.apikey.ApiKeyConfiguration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ProjectConfiguration extends Configuration implements ApiKeyBundleConfiguration{
  @JsonProperty("database")
  private DataSourceFactory database = new DataSourceFactory();

  @JsonProperty("authentication")
  @Valid
  @NotNull
  private ApiKeyConfiguration apiKeyConfiguration;

  public DataSourceFactory getDatabase() {
    return database;
  }

  @Override
  public ApiKeyConfiguration getApiKeyConfiguration() {
    return apiKeyConfiguration;
  }
}
