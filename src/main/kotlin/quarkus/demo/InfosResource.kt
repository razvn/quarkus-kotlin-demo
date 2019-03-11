package quarkus.demo

import org.eclipse.microprofile.rest.client.inject.RestClient
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

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