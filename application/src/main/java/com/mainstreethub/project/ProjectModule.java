package com.mainstreethub.project;

import com.google.common.base.Throwables;

import dagger.Module;
import dagger.Provides;

import io.split.client.SplitClient;
import io.split.client.SplitClientBuilder;
import io.split.client.SplitClientConfig;

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
      SplitClientConfig config = SplitClientConfig.builder().pollForFeatureChangesEveryNSeconds(10).build();
      return SplitClientBuilder.build(configuration.getSplitio().getApikey(), config);
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }
}
