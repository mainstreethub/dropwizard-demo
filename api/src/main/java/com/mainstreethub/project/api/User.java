package com.mainstreethub.project.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
  private final String username;
  private final String first;
  private final String last;

  /**
   * User object.
   * @param username User's username
   * @param first User's first name
   * @param last User's last name.
   */
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
