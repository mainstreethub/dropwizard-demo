package com.mainstreethub.project.dao;

import dagger.Module;
import dagger.Provides;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import javax.inject.Singleton;
import org.skife.jdbi.v2.DBI;

import static com.google.common.base.Preconditions.checkNotNull;

@Module
public class JdbiModule {
  private final DataSourceFactory configuration;
  private final Environment environment;

  public JdbiModule(DataSourceFactory configuration, Environment environment) {
    this.configuration = checkNotNull(configuration);
    this.environment = checkNotNull(environment);
  }

  @Singleton
  @Provides
  public DBIFactory provideDbiFactory() {
    return new DBIFactory();
  }

  @Singleton
  @Provides
  public DBI provideDbi(DBIFactory factory) {
    return factory.build(environment, configuration, "db");
  }

  @Singleton
  @Provides
  public UserDao provideUserDao(DBI dbi) {
    return dbi.onDemand(UserDao.class);
  }
}
