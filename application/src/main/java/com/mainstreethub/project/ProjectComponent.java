package com.mainstreethub.project;

import com.mainstreethub.project.dao.JdbiModule;
import com.mainstreethub.project.resources.UsersResource;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {JdbiModule.class, ProjectModule.class})
public interface ProjectComponent {
  UsersResource getUsersResource();
}
