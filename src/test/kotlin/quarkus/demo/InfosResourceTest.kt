package quarkus.demo

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
open class InfosResourceTest {

    @Test
    fun testHelloEndpoint() {
        given()
          .`when`().get("/infos")
          .then()
             .statusCode(200)
             .body(`is`("Hello Kotlin, from Quarkus service"))
    }

}