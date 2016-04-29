package com.mainstreethub.project.resources;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import io.split.client.SplitClient;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/neato")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class NeatoResource {
  private final SplitClient splitClient;
  private final Joiner joiner = Joiner.on(",");

  @Inject
  public NeatoResource(SplitClient splitClient) {
    this.splitClient = splitClient;
  }

  @Path("/{featureId}")
  @GET
  public String doSomethingNeat(@PathParam("featureId") String featureId, @QueryParam("user") String user, @Context UriInfo context) {
    Map<String, Object> queryParams = Maps.transformValues(context.getQueryParameters(), joiner::join);
    return splitClient.getTreatment(user, featureId, queryParams);
  }

}
