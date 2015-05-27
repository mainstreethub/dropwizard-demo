package com.mainstreethub.project.resources;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.mainstreethub.project.CreateUserRequest;
import com.mainstreethub.project.User;
import com.mainstreethub.project.dao.UserDAO;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.google.common.base.Preconditions.checkNotNull;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource {
  private final UserDAO dao;
  private final SecureRandom random;

  @Inject
  UsersResource(UserDAO dao, SecureRandom random) {
    this.dao = checkNotNull(dao);
    this.random = checkNotNull(random);
  }

  @POST
  public Response createUser(CreateUserRequest user) throws URISyntaxException {
    String salt = sha(random.nextLong());
    String hash = sha(salt + user.getPassword());
    long id = dao.createUser(user.getUsername(), user.getFirst(), user.getLast(), salt, hash);

    URI uri = new URI("/users/" + id);
    return Response.created(uri).build();
  }

  @GET
  @Path("/{id}")
  public Response findUser(@PathParam("id") long id) {
    UserDAO.DBUser dbUser = dao.findUserById(id);
    if (dbUser == null) {
      return Response.status(Response.Status.NOT_FOUND).entity("Could not find user").build();
    }

    User user = new User(dbUser.username, dbUser.first, dbUser.last);
    return Response.ok(user).build();
  }

  @DELETE
  @Path("/{id}")
  public Response deleteUser(@PathParam("id") long id) {
    int num = dao.deleteUser(id);
    if (num == 0) {
      return Response.status(Response.Status.NOT_FOUND).entity("Could not find user").build();
    }

    return Response.noContent().build();
  }

  @PUT
  @Path("/{id}/password")
  public Response updateUserPassword(@PathParam("id") long id, String password) {
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
