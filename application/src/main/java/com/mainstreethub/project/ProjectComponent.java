package com.mainstreethub.project;

import com.mainstreethub.project.dao.JDBIModule;
import com.mainstreethub.project.resources.UsersResource;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {JDBIModule.class, ProjectModule.class})
public interface ProjectComponent {
  UsersResource getUsersResource();
}
