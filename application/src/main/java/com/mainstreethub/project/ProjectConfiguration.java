package com.mainstreethub.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ProjectConfiguration extends Configuration {

  @Valid
  @NotNull
  private SplitioConfiguration splitio;

  @JsonProperty("database")
  private DataSourceFactory database = new DataSourceFactory();

  public DataSourceFactory getDatabase() {
    return database;
  }

  public SplitioConfiguration getSplitio() {
    return splitio;
  }
}
