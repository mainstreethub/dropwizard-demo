package com.mainstreethub.project;

import com.google.common.base.Throwables;

import dagger.Module;
import dagger.Provides;

import io.split.client.SplitClient;
import io.split.client.SplitClientBuilder;

import java.io.IOException;
import java.security.SecureRandom;
import javax.inject.Singleton;

@Module
public class ProjectModule {
  private final ProjectConfiguration configuration;

  public ProjectModule(ProjectConfiguration configuration) {
    this.configuration = configuration;
  }

  @Singleton
  @Provides
  public SecureRandom provideSecureRandom() {
    return new SecureRandom();
  }

  @Singleton
  @Provides
  public SplitClient provideSplitClient() {
    try {
      return SplitClientBuilder.build(configuration.getSplitio().getApikey());
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }
}
