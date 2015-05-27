package com.mainstreethub.project;

import dagger.Module;
import dagger.Provides;
import java.security.SecureRandom;
import javax.inject.Singleton;

@Module
public class ProjectModule {
  @Singleton
  @Provides
  public SecureRandom provideSecureRandom() {
    return new SecureRandom();
  }
}
