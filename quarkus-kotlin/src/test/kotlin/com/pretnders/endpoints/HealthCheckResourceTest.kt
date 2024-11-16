package com.pretnders.endpoints

import com.pretnders.shared.utils.shared_models.UserTypes
import com.pretnders.shared.security.JwtTokenGenerator
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.Cookie
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.hamcrest.Matchers.equalTo
@QuarkusTest
class HealthCheckResourceTest {

    var cookie : Cookie? = null

    @Inject
    @field:Default
    private lateinit var jwtTokenGenerator: JwtTokenGenerator

    @BeforeEach
    fun before() {
        val token = jwtTokenGenerator.getToken("123456","0712121212","sae.benn@gmail.com", UserTypes.PRETNDER.name)
        cookie = Cookie.Builder("Bearer", token)
            .setSecured(true) // Sets cookie to secure (optional)
            .setPath("/") // Defines the path (optional)
            .setMaxAge(64800) // Sets the cookie expiration to 3600 seconds (1 hour)
            .build()
    }

    @Test
    fun testHelloEndpoint() {
        given()
            .`when`().get("/healthcheck")
            .then()
            .statusCode(200)
            .body(`is`("Application is up and running"))
    }

    @Test
    fun testJwtOkEndpoint() {
        given()
            .`when`()
            .cookie(cookie)
            .get("/healthcheck/secured")
            .then()
            .statusCode(200)
            .body(equalTo("Application is up and running hahaha"))

    }

    @Test
    fun testSecuredWithNoJwtEndpoint() {
        given()
            .`when`()
            .get("/healthcheck/secured")
            .then()
            .statusCode(401)
    }

}