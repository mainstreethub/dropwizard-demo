package com.mainstreethub.project;

import com.mainstreethub.project.dao.JDBIModule;
import com.mainstreethub.project.dao.UserDAO;
import com.mainstreethub.project.resources.UsersResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import java.security.SecureRandom;
import org.skife.jdbi.v2.DBI;

public class ProjectApplication extends Application<ProjectConfiguration> {
  public static void main(String[] args) throws Exception {
    new ProjectApplication().run(args);
  }

  @Override
  public void run(ProjectConfiguration configuration, Environment environment) throws Exception {
    ProjectComponent component = DaggerProjectComponent.builder()
        .jDBIModule(new JDBIModule(configuration.getDatabaseConfiguration(), environment))
        .projectModule(new ProjectModule())
        .build();

    environment.jersey().register(component.getUsersResource());
  }
}
