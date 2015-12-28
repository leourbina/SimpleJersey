package com.leourbina.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.inject.Inject;
import com.google.inject.name.Named;

@Path("/guice-resource")
public class GuiceResource {
  private final String name;

  @Inject
  public GuiceResource(@Named("leourbina.name") String name) {
    this.name = name;
  }

  @GET
  public String getName() {
    return this.name;
  }
}
