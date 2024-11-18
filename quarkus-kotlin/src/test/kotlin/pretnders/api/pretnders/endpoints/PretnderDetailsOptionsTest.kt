package pretnders.api.pretnders.endpoints

import io.quarkus.logging.Log
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.junit.jupiter.api.Test
import pretnders.api.pretnders.application.dto.responses.FindDetailsOptionsResponse
import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ErrorResponse

@QuarkusTest
class PretnderDetailsOptionsTest {

    @Test
    fun shouldGetPretendersDetailsOptions() {
        val csrfToken = "123456"
        val res = RestAssured.given()
            .header("Content-Type", "application/json")
            .cookie("Bearer", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpY2kiLCJ1cG4iOiI4YTg3MGJmOGQzODY0NzVkODhlOTFmOTg3MjBmNWVmZSIsImdyb3VwcyI6WyJQUkVUTkRFUiJdLCJleHAiOjE3MzMxMTE3MDYsImFkZHJlc3MiOiJwZXRwYWxzLWFwcHMiLCJlbWFpbCI6InNhLmJlbm5hY2V1ckBnbWFpbC5jb20iLCJwaG9uZV9udW1iZXIiOiIwNzY0MDE3NTI4IiwiaWF0IjoxNzMxOTAyMTA2LCJqdGkiOiJhZGZiZmNmNy0xNjUyLTRkZGEtYmU0Yi1mY2M0NDY1MjhkNWMifQ.N7An4PPtbvMWzavCWycTdIQe1k7MTnDX7QUoGfvLTfbQA3Je7IqfDPb3Hz4QS7ZF72qQsIQ56bnDeSv5egxziEsmyK7wY8OCAZ7AV6YGNw2C75-ZCHC4rTh77kyQ0Jfx3YACytV20RGDxxCCsUhinJh706lOB67QYvJLf6L-hBeTq-i5-GZ6u_O1-iBTD3pQR2VzQ3rFMn8_Mj5Ve2fLlR2oh2AGtRhfZOcL4rfi_BH-G4UeY89N_Dkh23sL6o9B6HFqoO3T1Z4Z2gi_3qWhIA3cZ0GQmE4HA3SMnx5ksDsAX5i0lO6bLnJRhoIcR-nfvxmTksu3Sy-qAuw-cdqejQ")
            .header("x-csrf-token", csrfToken)
            .cookie("x-csrf-token", csrfToken)
            .`when`()
            .get("/pretnder-details-options")
            .then().extract()

        assert(200 == res.statusCode())
        val options = res.body().`as`( FindDetailsOptionsResponse::class.java)
        assert(7 == options.occurrences.size)
        assert(8 == options.orientations.size)
        assert(10 == options.bodyTypes.size)
    }

    @Test
    fun shouldFailOnCsrfToken() {
        val csrfToken = "123456"
        val res = RestAssured.given()
            .header("Content-Type", "application/json")
            .cookie("Bearer", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpY2kiLCJ1cG4iOiI4YTg3MGJmOGQzODY0NzVkODhlOTFmOTg3MjBmNWVmZSIsImdyb3VwcyI6WyJQUkVUTkRFUiJdLCJleHAiOjE3MzMxMTE3MDYsImFkZHJlc3MiOiJwZXRwYWxzLWFwcHMiLCJlbWFpbCI6InNhLmJlbm5hY2V1ckBnbWFpbC5jb20iLCJwaG9uZV9udW1iZXIiOiIwNzY0MDE3NTI4IiwiaWF0IjoxNzMxOTAyMTA2LCJqdGkiOiJhZGZiZmNmNy0xNjUyLTRkZGEtYmU0Yi1mY2M0NDY1MjhkNWMifQ.N7An4PPtbvMWzavCWycTdIQe1k7MTnDX7QUoGfvLTfbQA3Je7IqfDPb3Hz4QS7ZF72qQsIQ56bnDeSv5egxziEsmyK7wY8OCAZ7AV6YGNw2C75-ZCHC4rTh77kyQ0Jfx3YACytV20RGDxxCCsUhinJh706lOB67QYvJLf6L-hBeTq-i5-GZ6u_O1-iBTD3pQR2VzQ3rFMn8_Mj5Ve2fLlR2oh2AGtRhfZOcL4rfi_BH-G4UeY89N_Dkh23sL6o9B6HFqoO3T1Z4Z2gi_3qWhIA3cZ0GQmE4HA3SMnx5ksDsAX5i0lO6bLnJRhoIcR-nfvxmTksu3Sy-qAuw-cdqejQ")
            .header("x-csrf-token", "${csrfToken}7" )
            .cookie("x-csrf-token", csrfToken)
            .`when`()
            .get("/pretnder-details-options")
            .then().extract()
        assert(401 == res.statusCode())
        val error = res.body().`as`(ErrorResponse::class.java)

        assert( "Invalid CSRF" == error.message)
        assert( "APPLICATION" == error.origin)

    }

    @Test
    fun shouldFailOnBearer() {
        val csrfToken = "123456"
        val res = RestAssured.given()
            .header("Content-Type", "application/json")
            .cookie("Bearer", "eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpY2kiLCJ1cG4iOiI4YTg3MGJmOGQzODY0NzVkODhlOTFmOTg3MjBmNWVmZSIsImdyb3VwcyI6WyJQUkVUTkRFUiJdLCJleHAiOjE3MzMxMTE3MDYsImFkZHJlc3MiOiJwZXRwYWxzLWFwcHMiLCJlbWFpbCI6InNhLmJlbm5hY2V1ckBnbWFpbC5jb20iLCJwaG9uZV9udW1iZXIiOiIwNzY0MDE3NTI4IiwiaWF0IjoxNzMxOTAyMTA2LCJqdGkiOiJhZGZiZmNmNy0xNjUyLTRkZGEtYmU0Yi1mY2M0NDY1MjhkNWMifQ.N7An4PPtbvMWzavCWycTdIQe1k7MTnDX7QUoGfvLTfbQA3Je7IqfDPb3Hz4QS7ZF72qQsIQ56bnDeSv5egxziEsmyK7wY8OCAZ7AV6YGNw2C75-ZCHC4rTh77kyQ0Jfx3YACytV20RGDxxCCsUhinJh706lOB67QYvJLf6L-hBeTq-i5-GZ6u_O1-iBTD3pQR2VzQ3rFMn8_Mj5Ve2fLlR2oh2AGtRhfZOcL4rfi_BH-G4UeY89N_Dkh23sL6o9B6HFqoO3T1Z4Z2gi_3qWhIA3cZ0GQmE4HA3SMnx5ksDsAX5i0lO6bLnJRhoIcR-nfvxmTksu3Sy-qAuw-cdqejQ")
            .header("x-csrf-token", csrfToken )
            .cookie("x-csrf-token", csrfToken)
            .`when`()
            .get("/pretnder-details-options")
            .then().extract()
        Log.info(res.body().asString())
        assert(401 == res.statusCode())
    }
}