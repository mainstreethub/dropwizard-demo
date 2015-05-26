package com.mainstreethub.project;

import com.mainstreethub.project.dao.UserDAO;
import com.mainstreethub.project.resources.UsersResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class ProjectApplication extends Application<ProjectConfiguration> {
  public static void main(String[] args) throws Exception {
    new ProjectApplication().run(args);
  }

  @Override
  public void run(ProjectConfiguration configuration, Environment environment) throws Exception {
    DBIFactory factory = new DBIFactory();
    DBI dbi = factory.build(environment, configuration.getDatabaseConfiguration(), "db");

    UserDAO dao = dbi.onDemand(UserDAO.class);

    environment.jersey().register(new UsersResource(dao));
  }
}
