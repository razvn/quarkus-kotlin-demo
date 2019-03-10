package quarkus.demo

import javax.enterprise.context.ApplicationScoped


@ApplicationScoped
class InfosService {
    fun hello() = "Hello Kotlin, from Quarkus service"

}
