package com.leourbina.service;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {
  private final ObjectMapper defaultMapper;

  public ObjectMapperProvider() {
    this.defaultMapper = buildDefaultMapper();
  }

  private ObjectMapper buildDefaultMapper() {
    return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
  }

  @Override
  public ObjectMapper getContext(Class<?> type) {
    return defaultMapper;
  }
}
