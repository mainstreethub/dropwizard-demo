package com.mainstreethub.project.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest {
  private final String username;
  private final String first;
  private final String last;
  private final String password;

  /**
   * Creates a user request based on passed in parameters.
   * @param username username of request to create
   * @param first first name of user
   * @param last last name of user
   * @param password user's password
   */
  @JsonCreator
  public CreateUserRequest(@JsonProperty("username") String username,
                           @JsonProperty("first") String first,
                           @JsonProperty("last") String last,
                           @JsonProperty("password") String password) {
    this.username = username;
    this.first = first;
    this.last = last;
    this.password = password;
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

  @JsonProperty("password")
  public String getPassword() {
    return password;
  }
}
