Creating a service letting Guice handle instance binding is as simple ase:

```java
public class MyService {
  public static final String SERVICE_APP_ROOT = "/app";

  public static void main(String[] args) {
    new JerseyService().configure(new ServiceConfiguration.Builder()
        .setAppRoot(SERVICE_APP_ROOT)
        .setBasePackage(MyService.class.getPackage())
        .setModule(new MyGuiceModule())
        .build()
    ).run();
  }
  
  public static class MyModule extends AbstractModule {
    @Override
    protected void configure() {
      ...
    }
  }  
}
```

You can then create endpoints using Jersey annotations:

```java
@Path("string-resource")
public class StringResource {

  @GET
  public String getString() {
    return "Hello World!";
  }
}
```

That simple.

