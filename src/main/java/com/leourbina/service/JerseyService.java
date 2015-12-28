package com.leourbina.service;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.inject.servlet.ServletModule;
import com.google.inject.util.Modules;
import com.squarespace.jersey2.guice.BootstrapUtils;

public class JerseyService implements Service {
  private static final Logger LOG = LoggerFactory.getLogger(JerseyService.class);
  private static final String DEFAULT_SERVLET_PATH_SPEC = "/*";
  private Server server;

  @Override
  public Service configure(ServiceConfiguration configuration) {
    ServiceLocator locator = BootstrapUtils.newServiceLocator();
    BootstrapUtils.newInjector(locator, ImmutableList.of(
        Modules.combine(
            new ServletModule(), // This installs the Request scope
            configuration.module())
    ));
    BootstrapUtils.install(locator);

    final Server server = new Server(configuration.port());

    ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    contextHandler.setContextPath(configuration.appRoot());

    server.setHandler(contextHandler);
    contextHandler.addServlet(new ServletHolder(new ServletContainer(buildResourceConfig(configuration))), DEFAULT_SERVLET_PATH_SPEC);

    this.server = server;

    return this;
  }

  @Override
  public void run() {
    run(true);
  }

  @Override
  public void run(boolean join) {
    LOG.info("Starting!!!");
    try {
      server.start();
      if (join) {
        server.join();
      }
    } catch (Exception e) {
      Throwables.propagate(e);
    }
  }

  @Override
  public void stop() {
    try {
      server.stop();
    } catch (Exception e) {
      Throwables.propagate(e);
    } finally {
      if (server != null) {
        server.destroy();
        server = null;
      }
    }
    LOG.info("'Til next time");
  }

  private ResourceConfig buildResourceConfig(ServiceConfiguration serviceConfig) {
    ResourceConfig config = new ResourceConfig()
        .packages(serviceConfig.basePackage().getName())
        .register(ObjectMapperProvider.class)
        .register(JacksonFeature.class);

    return config;
  }
}
