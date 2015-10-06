package com.mainstreethub.project.client;


import com.mainstreethub.project.api.User;
import retrofit.http.GET;
import retrofit.http.Path;


public interface ProjectClient {
  @GET("/users/{id}")
  User getUserById(@Path("id") long userId);
}
