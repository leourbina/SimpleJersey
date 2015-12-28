package com.leourbina.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("string-resource")
public class StringResource {

  @GET
  public String getString() {
    return "Hello World!";
  }
}
