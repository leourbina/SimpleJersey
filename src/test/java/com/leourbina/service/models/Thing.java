package com.leourbina.service.models;

public class Thing {
  private String name;
  private String message;

  public Thing() {}

  public Thing(String name, String message) {
    this.name = name;
    this.message = message;
  }

  public String getName() {
    return name;
  }

  public String getMessage() {
    return message;
  }
}
