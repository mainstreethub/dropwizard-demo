package com.mainstreethub.project;

import com.google.common.base.Throwables;
import com.mainstreethub.project.dao.JDBIModule;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.flyway.FlywayBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.bundles.version.VersionBundle;
import io.dropwizard.bundles.version.VersionSupplier;
import io.dropwizard.bundles.version.suppliers.MavenVersionSupplier;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;


public class ProjectApplication extends Application<ProjectConfiguration> {
  public static void main(String[] args) throws Exception {
    new ProjectApplication().run(args);
  }


  @Override
  public void initialize(Bootstrap<ProjectConfiguration> bootstrap) {
    initVersion(bootstrap);
    initFlyway(bootstrap);
  }

  @Override
  public void run(ProjectConfiguration configuration, Environment environment) throws Exception {
    //Flyway will migrate or init the scheme in config.yaml
    final Flyway flyway = new Flyway();
    DataSourceFactory database = configuration.getDatabase();
    flyway.setDataSource(database.getUrl(), database.getUser(), database.getPassword());
    try {
      flyway.migrate();
    } catch (FlywayException e) {
      flyway.repair();
      //don't start app after failed migration attempt
      Throwables.propagate(e);
    }

    ProjectComponent component = DaggerProjectComponent.builder()
        .jDBIModule(new JDBIModule(configuration.getDatabase(), environment))
        .projectModule(new ProjectModule())
        .build();

    environment.jersey().register(component.getUsersResource());
  }

  private void initFlyway(Bootstrap<ProjectConfiguration> bootstrap) {
    bootstrap.addBundle(new FlywayBundle<ProjectConfiguration>() {
      @Override
      public PooledDataSourceFactory getDataSourceFactory(ProjectConfiguration configuration) {
        return configuration.getDatabase();
      }
    });
  }

  private void initVersion(Bootstrap<ProjectConfiguration> bootstrap) {
      VersionSupplier supplier = new MavenVersionSupplier("com.mainstreethub.project", "application");
      bootstrap.addBundle(new VersionBundle(supplier));
  }
}
