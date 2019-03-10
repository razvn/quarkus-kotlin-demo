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