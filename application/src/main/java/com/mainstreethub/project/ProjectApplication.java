package com.mainstreethub.project;

import com.mainstreethub.project.dao.JDBIModule;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.bundles.version.VersionBundle;
import io.dropwizard.bundles.version.VersionSupplier;
import io.dropwizard.bundles.version.suppliers.MavenVersionSupplier;


public class ProjectApplication extends Application<ProjectConfiguration> {
  public static void main(String[] args) throws Exception {
    new ProjectApplication().run(args);
  }


  @Override
  public void initialize(Bootstrap<ProjectConfiguration> bootstrap) {
      initVersion(bootstrap);
  }

  @Override
  public void run(ProjectConfiguration configuration, Environment environment) throws Exception {
    ProjectComponent component = DaggerProjectComponent.builder()
        .jDBIModule(new JDBIModule(configuration.getDatabase(), environment))
        .projectModule(new ProjectModule())
        .build();

    environment.jersey().register(component.getUsersResource());
  }

  private void initVersion(Bootstrap<ProjectConfiguration> bootstrap) {
      VersionSupplier supplier = new MavenVersionSupplier("com.mainstreethub.project", "application");
      bootstrap.addBundle(new VersionBundle(supplier));
  }
}
