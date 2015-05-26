package com.mainstreethub.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class ProjectConfiguration extends Configuration {
  @JsonProperty("database")
  private DataSourceFactory database = new DataSourceFactory();

  public DataSourceFactory getDatabaseConfiguration() {
    return database;
  }
}
