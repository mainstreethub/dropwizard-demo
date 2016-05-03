package com.mainstreethub.project.resources;

import com.google.common.collect.ImmutableMap;

import io.dropwizard.jersey.params.DateTimeParam;
import io.split.client.SplitClient;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/neato")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class NeatoResource {
  private final SplitClient splitClient;

  @Inject
  public NeatoResource(SplitClient splitClient) {
    this.splitClient = splitClient;
  }

  @Path("/{featureId}")
  @GET
  public String doSomethingNeat(@PathParam("featureId") String featureId,
                                @QueryParam("user") String user,
                                @QueryParam("industry") @DefaultValue("UNKNOWN") String industry,
                                @QueryParam("startDate") @DefaultValue("1970-01-01") DateTimeParam startDate,
                                @QueryParam("isBeta") @DefaultValue("false") Boolean isBeta
  ) {
    return splitClient.getTreatment(user, featureId, ImmutableMap.of(
            "industry", industry,
            "startDate", startDate.get().toDate().getTime(),
            "isBeta", isBeta
    ));
  }

}
