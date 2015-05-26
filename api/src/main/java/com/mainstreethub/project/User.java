package com.mainstreethub.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
  private final String username;
  private final String first;
  private final String last;

  @JsonCreator
  public User(@JsonProperty("username") String username,
              @JsonProperty("first") String first,
              @JsonProperty("last") String last) {
    this.username = username;
    this.first = first;
    this.last = last;
  }

  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  @JsonProperty("first")
  public String getFirst() {
    return first;
  }

  @JsonProperty("last")
  public String getLast() {
    return last;
  }
}
