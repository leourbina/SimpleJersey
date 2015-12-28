package com.leourbina.service;

import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.hubspot.horizon.HttpClient;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpRequest.ContentType;
import com.hubspot.horizon.HttpRequest.Method;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.horizon.apache.ApacheHttpClient;
import com.leourbina.service.models.Thing;
import com.leourbina.service.util.BaseModule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceTest {
  private static final int TEST_PORT = 8090;
  private static final String TEST_APP_ROOT = "/coolshit";
  private static Service SERVICE;
  private static HttpClient HTTP;

  private static class TestModule extends BaseModule {
    @Override
    protected void configure() {
      install(new ServletModule());
      bind(String.class)
          .annotatedWith(Names.named("leourbina.getName"))
          .toInstance("Guice Works!");
    }
  }

  @BeforeClass
  public static void setup() {
    SERVICE = new JerseyService()
        .configure(
            new ServiceConfiguration.Builder()
                .setPort(8090)
                .setAppRoot("/coolshit")
                .setBasePackage(ServiceTest.class.getPackage())
                .setModule(new TestModule())
                .build()
        );
    SERVICE.run(false);

    HTTP = new ApacheHttpClient();
  }

  @AfterClass
  public static void teardown() {
    SERVICE.stop();
  }

  @Test
  public void itCanGetThing() {
    String uri = String.format("http://localhost:%d%s/json-resource", TEST_PORT, TEST_APP_ROOT);
    HttpRequest request = HttpRequest.newBuilder()
        .setUrl(uri)
        .setMethod(Method.GET)
        .setContentType(ContentType.TEXT)
        .build();

    HttpResponse response = HTTP.execute(request);
    assertThat(response.getStatusCode()).isEqualTo(200);
    Thing thing = response.getAs(Thing.class);
    assertThat(thing.getName()).isEqualToIgnoringCase("thing");
    assertThat(thing.getMessage()).isEqualToIgnoringCase("hello world");
  }

  @Test
  public void itCanGetString() {
    String uri = String.format("http://localhost:%d%s/string-resource", TEST_PORT, TEST_APP_ROOT);
    HttpRequest request = HttpRequest.newBuilder()
        .setUrl(uri)
        .setMethod(Method.GET)
        .setContentType(ContentType.TEXT)
        .build();

    HttpResponse response = HTTP.execute(request);
    assertThat(response.getStatusCode()).isEqualTo(200);
  }

  @Test
  public void itCanUseGuice() {
    String uri = String.format("http://localhost:%d%s/guice-resource", TEST_PORT, TEST_APP_ROOT);
    HttpRequest request = HttpRequest.newBuilder()
        .setUrl(uri)
        .setMethod(Method.GET)
        .setContentType(ContentType.TEXT)
        .build();

    HttpResponse response = HTTP.execute(request);
    assertThat(response.getStatusCode()).isEqualTo(200);
    assertThat(response.getAsString()).isEqualToIgnoringCase("guice works!");
  }
}