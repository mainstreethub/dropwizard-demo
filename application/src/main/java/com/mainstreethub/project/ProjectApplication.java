package com.mainstreethub.project;

import com.mainstreethub.project.dao.JdbiModule;

import io.dropwizard.Application;
import io.dropwizard.bundles.apikey.ApiKeyBundle;
import io.dropwizard.bundles.version.VersionBundle;
import io.dropwizard.bundles.version.VersionSupplier;
import io.dropwizard.bundles.version.suppliers.MavenVersionSupplier;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class ProjectApplication extends Application<ProjectConfiguration> {
  public static void main(String[] args) throws Exception {
    new ProjectApplication().run(args);
  }

  @Override
  public void initialize(Bootstrap<ProjectConfiguration> bootstrap) {
    initVersion(bootstrap);
    bootstrap.addBundle(new ApiKeyBundle<ProjectConfiguration>());
  }

  @Override
  public void run(ProjectConfiguration configuration, Environment environment) throws Exception {
    ProjectComponent component = DaggerProjectComponent.builder()
            .jdbiModule(new JdbiModule(configuration.getDatabase(), environment))
            .projectModule(new ProjectModule())
            .build();
    environment.jersey().register(component.getUsersResource());
  }

  private void initVersion(Bootstrap<ProjectConfiguration> bootstrap) {
    VersionSupplier supplier = new MavenVersionSupplier("com.mainstreethub.project", "application");
    bootstrap.addBundle(new VersionBundle(supplier));
  }
}
