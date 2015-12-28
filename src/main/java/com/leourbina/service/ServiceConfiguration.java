package com.leourbina.service;

import com.google.common.base.Preconditions;
import com.google.inject.Module;

public class ServiceConfiguration {
  private Package basePackage;
  private Module module;
  private int port;
  private String appRoot;

  private ServiceConfiguration() {}

  private ServiceConfiguration(Package basePackage,
                               Module module,
                               int port,
                               String appRoot) {
    this.basePackage = basePackage;
    this.module = module;
    this.port = port;
    this.appRoot = appRoot;
  }

  public Package basePackage() {
    return basePackage;
  }

  public Module module() {
    return module;
  }

  public int port() {
    return port;
  }

  public String appRoot() {
    return appRoot;
  }

  public static class Builder extends ServiceConfiguration {
    public static final int DEFAULT_PORT = 8080;
    public static final String DEFAULT_APP_ROOT = "/";

    private Package basePackage = null;
    private Module module = null;
    private int port = DEFAULT_PORT;
    private String appRoot = DEFAULT_APP_ROOT;

    public Builder setBasePackage(Package basePackage) {
      Preconditions.checkNotNull(basePackage);
      this.basePackage = basePackage;
      return this;
    }

    public Builder setModule(Module module) {
      Preconditions.checkNotNull(module);
      this.module = module;
      return this;
    }

    public Builder setPort(int port) {
      this.port = port;
      return this;
    }

    public Builder setAppRoot(String appRoot) {
      Preconditions.checkNotNull(appRoot);
      this.appRoot = appRoot;
      return this;
    }

    public ServiceConfiguration build() {
      Preconditions.checkNotNull(basePackage);
      Preconditions.checkNotNull(basePackage);
      Preconditions.checkNotNull(appRoot);

      return new ServiceConfiguration(basePackage, module, port, appRoot);
    }
  }
}
