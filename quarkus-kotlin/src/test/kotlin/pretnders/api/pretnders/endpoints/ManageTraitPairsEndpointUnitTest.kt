package pretnders.api.pretnders.endpoints

import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import pretnders.api.pretnders.application.dto.requests.ChangeTraitScoreRequest
import pretnders.api.pretnders.domain.ports.`in`.ChangeTraitScoreIn


@QuarkusTest
class ManageTraitPairsEndpointUnitTest {

    @InjectMock
    private lateinit var changeTraitScoreIn: ChangeTraitScoreIn

    @ParameterizedTest
    @ValueSource(shorts = [10, 20, 30, 50, 70, 90, 100])
    @DisplayName("Should change trait pair score in endpoint")
    fun shouldChangeTraitPairScore(score:Short){
        val request = ChangeTraitScoreRequest(
            "123456789",
            score
        )

        val csrfToken = "X12Z78S45e89"
        val mapper = ObjectMapper()
        val jsonBody = mapper.writeValueAsString(request)
        doNothing().`when`(changeTraitScoreIn).updateScore(request.traitPairReference, request.score)
        given()
            .header("Content-Type", "application/json")
            .cookie("Bearer", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpY2kiLCJ1cG4iOiI4YTg3MGJmOGQzODY0NzVkODhlOTFmOTg3MjBmNWVmZSIsImdyb3VwcyI6WyJQUkVUTkRFUiJdLCJleHAiOjE3MzMxMTE3MDYsImFkZHJlc3MiOiJwZXRwYWxzLWFwcHMiLCJlbWFpbCI6InNhLmJlbm5hY2V1ckBnbWFpbC5jb20iLCJwaG9uZV9udW1iZXIiOiIwNzY0MDE3NTI4IiwiaWF0IjoxNzMxOTAyMTA2LCJqdGkiOiJhZGZiZmNmNy0xNjUyLTRkZGEtYmU0Yi1mY2M0NDY1MjhkNWMifQ.N7An4PPtbvMWzavCWycTdIQe1k7MTnDX7QUoGfvLTfbQA3Je7IqfDPb3Hz4QS7ZF72qQsIQ56bnDeSv5egxziEsmyK7wY8OCAZ7AV6YGNw2C75-ZCHC4rTh77kyQ0Jfx3YACytV20RGDxxCCsUhinJh706lOB67QYvJLf6L-hBeTq-i5-GZ6u_O1-iBTD3pQR2VzQ3rFMn8_Mj5Ve2fLlR2oh2AGtRhfZOcL4rfi_BH-G4UeY89N_Dkh23sL6o9B6HFqoO3T1Z4Z2gi_3qWhIA3cZ0GQmE4HA3SMnx5ksDsAX5i0lO6bLnJRhoIcR-nfvxmTksu3Sy-qAuw-cdqejQ")
            .cookie("x-csrf-token", csrfToken)
            .header("x-csrf-token", csrfToken)
            .body(jsonBody)
            .`when`()
            .put("/pretnder-traits")
            .then()
            .statusCode(204)

        verify(changeTraitScoreIn).updateScore(request.traitPairReference, request.score)
    }
}