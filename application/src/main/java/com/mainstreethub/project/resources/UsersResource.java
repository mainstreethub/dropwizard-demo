package com.mainstreethub.project.resources;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import static com.google.common.base.Preconditions.checkNotNull;

import com.mainstreethub.project.api.User;
import com.mainstreethub.project.api.CreateUserRequest;
import com.mainstreethub.project.dao.UserDao;
import io.dropwizard.auth.Auth;
import javax.inject.Inject;
import java.net.URISyntaxException;
import java.net.URI;
import java.security.SecureRandom;
import javax.ws.rs.Consumes;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.PUT;


@Path("/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource {
  private final UserDao dao;
  private final SecureRandom random;

  @Inject
  UsersResource(UserDao dao, SecureRandom random) {
    this.dao = checkNotNull(dao);
    this.random = checkNotNull(random);
  }

  /**
   * Creates a user in the database.
   * @param keyName keyname of app requesting this resource
   * @param user user to create
   * @return 201 if successful.
   * @throws URISyntaxException Syntax of the request is incorrect
   */
  @POST
  public Response createUser(@Auth String keyName,
                             CreateUserRequest user) throws URISyntaxException {
    String salt = sha(random.nextLong());
    String hash = sha(salt + user.getPassword());
    long id = dao.createUser(user.getUsername(), user.getFirst(), user.getLast(), salt, hash);

    URI uri = new URI("/users/" + id);
    return Response.created(uri).build();
  }

  /**
   * Returns a user in the database.
   * @param keyName keyname of app requesting this resource
   * @param id ID of user to return
   * @return 200 on success, with User object.
   */
  @GET
  @Path("/{id}")
  public Response findUser(@Auth String keyName,
                           @PathParam("id") long id) {
    UserDao.DbUser dbUser = dao.findUserById(id);
    if (dbUser == null) {
      return Response.status(Response.Status.NOT_FOUND).entity("Could not find user").build();
    }

    User user = new User(dbUser.username, dbUser.first, dbUser.last);
    return Response.ok(user).build();
  }

  /**
   * Deletes a user in the database.
   * @param key ApiKey to use
   * @param id ID of user to delete
   * @return 204 if deleted, 404 if not found
   */
  @DELETE
  @Path("/{id}")
  public Response deleteUser(@Auth String key,
                             @PathParam("id") long id) {
    int num = dao.deleteUser(id);
    if (num == 0) {
      return Response.status(Response.Status.NOT_FOUND).entity("Could not find user").build();
    }

    return Response.noContent().build();
  }

  /**
   * Updates the password of a specified user.
   * @param key ApiKey to use
   * @param id User ID to update password of.
   * @param password new password
   * @return 204 if updated, 404 if not found.
   */
  @PUT
  @Path("/{id}/password")
  public Response updateUserPassword(@Auth String key,
                                     @PathParam("id") long id,
                                     String password) {
    String salt = sha(random.nextLong());
    String hash = sha(salt + password);
    int num = dao.updatePassword(id, salt, hash);

    if (num == 0) {
      return Response.status(Response.Status.NOT_FOUND).entity("Could not find user").build();
    }

    return Response.noContent().build();
  }

  private static String sha(long l) {
    return Hashing.sha256().hashLong(l).toString();
  }

  private static String sha(String s) {
    return Hashing.sha256().hashString(s, Charsets.UTF_8).toString();
  }
}
