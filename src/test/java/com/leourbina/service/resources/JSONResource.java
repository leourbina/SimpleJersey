package com.leourbina.service.resources;

import com.leourbina.service.models.Thing;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/json-resource")
@Produces(MediaType.APPLICATION_JSON)
public class JSONResource {

  @GET
  public Thing getThing(@QueryParam("name") @DefaultValue("thing") String name) {
    return new Thing(name, "hello world");
  }
}
