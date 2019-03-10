# Quarkus Kotlin Demo

A test repo of [Quarkus](https://quarkus.io) and its Kotlin support.

Run the app:
```
mvn compile quarkus:dev
```

Test end point:
```
curl http://120.0.0.1:8080/infos
```

## Contructor-injection problem (SOLVED)

I followed the Java example from [Context and dependency injection Guide](https://quarkus.io/guides/cdi-reference)

In `InfosResource` I added a secondary constructor (as primary is  said to: *Dummy constructor needed for CDI beans with a normal scope*)

```kotlin
@Path("/infos")
class InfosResource {

    private var infosService: InfosService? = null

    @Inject
    constructor(infosService: InfosService) {
        this.infosService = infosService
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = infosService?.hello()
}
```

Applicartion fails to start

```
ERROR [io.qua.dev.DevModeMain] (main) Failed to start quarkus: java.lang.ExceptionInInitializerError
Caused by: java.lang.RuntimeException: java.lang.RuntimeException: RESTEASY003190: Could not find constructor for class: quarkus.demo.InfosResource
 
```

Same goes if I try to make it primary constructor
```kotlin
@Path("/infos")
class InfosResource @Inject constructor(val infosService: InfosService) {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = infosService.hello()
}
```

Turns out it's my bad understanding of secondary constructor in Kotlin.
According to [documentation](https://kotlinlang.org/docs/reference/classes.html) it means both not default constructor (as in my case),
so it's a secondary constructor with no primary, and a secondary constructor beside the primary one.

So I was missing the primary default 'dummy' constructor, problem **SOLVED**

```kotlin
@Path("/infos")
class InfosResource() {

    private var infosService: InfosService? = null

    @Inject
    constructor(infosService: InfosService):this() {
        this.infosService = infosService
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = infosService?.hello()
}
```

## Other branches

Checkout the other branches:
 - `master`: working simple demo
 - `rest-client`: Trying to use RestClient in Kotlin
