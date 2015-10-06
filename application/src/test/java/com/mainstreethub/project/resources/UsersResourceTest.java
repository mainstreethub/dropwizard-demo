package com.mainstreethub.project.resources;

import com.mainstreethub.project.api.User;
import com.mainstreethub.project.dao.UserDao;
import java.security.SecureRandom;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class UsersResourceTest {
  private static final UserDao.DbUser DB_USER = new UserDao.DbUser("user1", "first1", "last1");

  private final UserDao dao = Mockito.mock(UserDao.class);
  private final SecureRandom random = new SecureRandom();
  private final UsersResource resource = new UsersResource(dao, random);
  private final String testKey = "CHANGEME";

  @Test
  public void testFindExistingUser() {
    Mockito.when(dao.findUserById(1L)).thenReturn(DB_USER);

    Response response = resource.findUser(testKey, 1L);
    assertEquals(Response.Status.OK, response.getStatusInfo());

    User user = (User) response.getEntity();
    assertEquals(DB_USER.username, user.getUsername());
    assertEquals(DB_USER.first, user.getFirst());
    assertEquals(DB_USER.last, user.getLast());
  }

  @Test
  public void testFindMissingUser() {
    Mockito.when(dao.findUserById(1L)).thenReturn(null);

    Response response = resource.findUser(testKey, 1L);
    assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
  }
}
