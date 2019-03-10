package quarkus.demo

import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

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