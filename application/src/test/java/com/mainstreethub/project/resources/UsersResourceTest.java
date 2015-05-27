package com.mainstreethub.project.resources;

import com.mainstreethub.project.User;
import com.mainstreethub.project.dao.UserDAO;
import java.security.SecureRandom;
import javax.ws.rs.core.Response;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class UsersResourceTest {
  private static final UserDAO.DBUser DB_USER = new UserDAO.DBUser("user1", "first1", "last1");

  private final UserDAO dao = Mockito.mock(UserDAO.class);
  private final SecureRandom random = new SecureRandom();
  private final UsersResource resource = new UsersResource(dao, random);

  @Test
  public void testFindExistingUser() {
    Mockito.when(dao.findUserById(1L)).thenReturn(DB_USER);

    Response response = resource.findUser(1L);
    assertEquals(Response.Status.OK, response.getStatusInfo());

    User user = (User) response.getEntity();
    assertEquals(DB_USER.username, user.getUsername());
    assertEquals(DB_USER.first, user.getFirst());
    assertEquals(DB_USER.last, user.getLast());
  }

  @Test
  public void testFindMissingUser() {
    Mockito.when(dao.findUserById(1L)).thenReturn(null);

    Response response = resource.findUser(1L);
    assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
  }
}
