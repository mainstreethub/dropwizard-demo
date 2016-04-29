package com.mainstreethub.project;

import org.hibernate.validator.constraints.NotEmpty;

public class SplitioConfiguration {

  @NotEmpty
  private String apikey;

  public String getApikey() {
    return apikey;
  }
}
