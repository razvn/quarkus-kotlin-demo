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

## Rest client Kotlin problem (**SOLVED-PARTIALLY**)

I followed the Java example from [Using the Rest Client](https://quarkus.io/guides/rest-client-guide)

I plan to use the service [JsonPlaceHolder todo Service](https://jsonplaceholder.typicode.com/todos) 

Added the model:

```kotlin
data class ToDo(
        val userId: Int,
        val id: Int,
        val title: String,
        val completed: Boolean
)
```

Then created the interface:

```kotlin
package quarkus.demo

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Path("/")
@RegisterRestClient
interface ToDoService {

    @GET
    @Path("/todos")
    @Produces("application/json")
    fun getToDos():Set<ToDo>
}
```

Added the `application.properties`:

```properties
quarkus.demo.ToDoService/mp-rest/url=https://jsonplaceholder.typicode.com
```

Updated the resource with injecting the interface and using it 

```kotlin
package quarkus.demo

import org.eclipse.microprofile.rest.client.inject.RestClient
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/infos")
class InfosResource {

    @Inject
    private lateinit var infosService: InfosService

    @Inject
    @RestClient
    private lateinit var toDoService: ToDoService

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = infosService.hello()

    @GET
    @Path("/todos")
    @Produces(MediaType.APPLICATION_JSON)
    fun todos() = toDoService.getToDos()
}
```

Running the app in dev mode `mvn compile quarkus:dev` just returns Build Success without starting the app.
It's a [known issue](https://github.com/quarkusio/quarkus/issues/1363) that.

So tried `mvn clean install` which fails with:

```
[ERROR] quarkus.demo.InfosResourceTest  Time elapsed: 1.827 s  <<< ERROR!
java.lang.RuntimeException: 
org.jboss.builder.BuildException: Build failure: Build failed due to errors
        [error]: Build step io.quarkus.arc.deployment.ArcAnnotationProcessor#build threw an exception: javax.enterprise.inject.spi.DeploymentException: javax.enterprise.inject.UnsatisfiedResolutionException: Unsatisfied dependency for type quarkus.demo.ToDoService and qualifiers [@Default]
        - java member: quarkus.demo.InfosResource#toDoService
        - declared on CLASS bean [types=[java.lang.Object, quarkus.demo.InfosResource], qualifiers=[@Default, @Any], target=quarkus.demo.InfosResource]
Caused by: org.jboss.builder.BuildException: 
Build failure: Build failed due to errors
        [error]: Build step io.quarkus.arc.deployment.ArcAnnotationProcessor#build threw an exception: javax.enterprise.inject.spi.DeploymentException: javax.enterprise.inject.UnsatisfiedResolutionException: Unsatisfied dependency for type quarkus.demo.ToDoService and qualifiers [@Default]
        - java member: quarkus.demo.InfosResource#toDoService
        - declared on CLASS bean [types=[java.lang.Object, quarkus.demo.InfosResource], qualifiers=[@Default, @Any], target=quarkus.demo.InfosResource]
Caused by: javax.enterprise.inject.spi.DeploymentException: 
javax.enterprise.inject.UnsatisfiedResolutionException: Unsatisfied dependency for type quarkus.demo.ToDoService and qualifiers [@Default]
        - java member: quarkus.demo.InfosResource#toDoService
        - declared on CLASS bean [types=[java.lang.Object, quarkus.demo.InfosResource], qualifiers=[@Default, @Any], target=quarkus.demo.InfosResource]
Caused by: javax.enterprise.inject.UnsatisfiedResolutionException: 
Unsatisfied dependency for type quarkus.demo.ToDoService and qualifiers [@Default]
        - java member: quarkus.demo.InfosResource#toDoService
        - declared on CLASS bean [types=[java.lang.Object, quarkus.demo.InfosResource], qualifiers=[@Default, @Any], target=quarkus.demo.InfosResource]
```

## SOLVED Injection

Injection problem can be solved using:

```kotlin
    @Inject
    @field: RestClient
    private lateinit var toDoService: ToDoService
```

Or constructor injection: 

```kotlin
@Path("/infos")
class InfosResource() {
    private var infosService: InfosService? = null
    private var toDoService: ToDoService? = null

    @Inject
    constructor(infosService: InfosService, @RestClient toDoService: ToDoService):this() {
        this.infosService = infosService
        this.toDoService = toDoService
    }

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = infosService?.hello()

    @GET
    @Path("/todos")
    @Produces(MediaType.APPLICATION_JSON)
    fun todos() = toDoService?.getToDos()
}
```

Also, in order to get an answer `ToDo` data class must have default constructor so it becomes:
```kotlin
data class ToDo(
        val userId: Int? = null,
        val id: Int? = null,
        val title: String? = null,
        val completed: Boolean = false
)
```
 
## Remaining problem

Yet if I call:
```
curl http://localhost:8080/infos/todos    
```
I get a list with an item with default constructor....
```json
[{"completed":false}]
```

## Other branches

Checkout the other branches for different problems:
 - `master`: working simple demo
 - `constructor-injection`: constructor injection in Kotlin (**SOLVED**)
