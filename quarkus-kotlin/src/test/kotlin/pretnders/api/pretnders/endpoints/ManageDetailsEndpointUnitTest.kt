package pretnders.api.pretnders.endpoints

import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.eq
import pretnders.api.pretnders.application.dto.requests.ChangeBiographyRequest
import pretnders.api.pretnders.application.dto.requests.ChangeNicknameRequest
import pretnders.api.pretnders.domain.models.details_enums.*
import pretnders.api.pretnders.domain.ports.`in`.ChangeDetailsIn

@QuarkusTest
@DisplayName("Endpoint details update unit tests")
class ManageDetailsEndpointUnitTest {

    @InjectMock
    private lateinit var changeDetailsIn: ChangeDetailsIn

    @ParameterizedTest
    @ValueSource(strings = ["VEGAN", "VEGETARIAN","OMNIVORE"])
    @DisplayName("Should change diet endpoint")
    fun shouldChangeDiet(diet:String){
        val detailsReference = "123456789"
        val csrfToken = "X12Z78S45e89"
        doNothing().`when`(changeDetailsIn).changeDiet(detailsReference, Diets.valueOf(diet))
        given()
            .header("Content-Type", "application/json")
            .cookie("Bearer", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpY2kiLCJ1cG4iOiI4YTg3MGJmOGQzODY0NzVkODhlOTFmOTg3MjBmNWVmZSIsImdyb3VwcyI6WyJQUkVUTkRFUiJdLCJleHAiOjE3MzMxMTE3MDYsImFkZHJlc3MiOiJwZXRwYWxzLWFwcHMiLCJlbWFpbCI6InNhLmJlbm5hY2V1ckBnbWFpbC5jb20iLCJwaG9uZV9udW1iZXIiOiIwNzY0MDE3NTI4IiwiaWF0IjoxNzMxOTAyMTA2LCJqdGkiOiJhZGZiZmNmNy0xNjUyLTRkZGEtYmU0Yi1mY2M0NDY1MjhkNWMifQ.N7An4PPtbvMWzavCWycTdIQe1k7MTnDX7QUoGfvLTfbQA3Je7IqfDPb3Hz4QS7ZF72qQsIQ56bnDeSv5egxziEsmyK7wY8OCAZ7AV6YGNw2C75-ZCHC4rTh77kyQ0Jfx3YACytV20RGDxxCCsUhinJh706lOB67QYvJLf6L-hBeTq-i5-GZ6u_O1-iBTD3pQR2VzQ3rFMn8_Mj5Ve2fLlR2oh2AGtRhfZOcL4rfi_BH-G4UeY89N_Dkh23sL6o9B6HFqoO3T1Z4Z2gi_3qWhIA3cZ0GQmE4HA3SMnx5ksDsAX5i0lO6bLnJRhoIcR-nfvxmTksu3Sy-qAuw-cdqejQ")
            .cookie("x-csrf-token", csrfToken)
            .header("x-csrf-token", csrfToken)
            .`when`()
            .put("/pretnder-profile/diet/$detailsReference/$diet")
            .then()
            .statusCode(204)
    }

    @ParameterizedTest
    @ValueSource(strings = ["LEAN", "THIN","FULL_FIGURED"])
    @DisplayName("Should change body type endpoint")
    fun shouldChangeBodyType(bodType:String){
        val detailsReference = "123456789"
        val csrfToken = "X12Z78S45e89"
        doNothing().`when`(changeDetailsIn).changeBodyType(detailsReference, BodyTypes.valueOf(bodType))
        given()
            .header("Content-Type", "application/json")
            .cookie("Bearer", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpY2kiLCJ1cG4iOiI4YTg3MGJmOGQzODY0NzVkODhlOTFmOTg3MjBmNWVmZSIsImdyb3VwcyI6WyJQUkVUTkRFUiJdLCJleHAiOjE3MzMxMTE3MDYsImFkZHJlc3MiOiJwZXRwYWxzLWFwcHMiLCJlbWFpbCI6InNhLmJlbm5hY2V1ckBnbWFpbC5jb20iLCJwaG9uZV9udW1iZXIiOiIwNzY0MDE3NTI4IiwiaWF0IjoxNzMxOTAyMTA2LCJqdGkiOiJhZGZiZmNmNy0xNjUyLTRkZGEtYmU0Yi1mY2M0NDY1MjhkNWMifQ.N7An4PPtbvMWzavCWycTdIQe1k7MTnDX7QUoGfvLTfbQA3Je7IqfDPb3Hz4QS7ZF72qQsIQ56bnDeSv5egxziEsmyK7wY8OCAZ7AV6YGNw2C75-ZCHC4rTh77kyQ0Jfx3YACytV20RGDxxCCsUhinJh706lOB67QYvJLf6L-hBeTq-i5-GZ6u_O1-iBTD3pQR2VzQ3rFMn8_Mj5Ve2fLlR2oh2AGtRhfZOcL4rfi_BH-G4UeY89N_Dkh23sL6o9B6HFqoO3T1Z4Z2gi_3qWhIA3cZ0GQmE4HA3SMnx5ksDsAX5i0lO6bLnJRhoIcR-nfvxmTksu3Sy-qAuw-cdqejQ")
            .cookie("x-csrf-token", csrfToken)
            .header("x-csrf-token", csrfToken)
            .`when`()
            .put("/pretnder-profile/body-type/$detailsReference/$bodType")
            .then()
            .statusCode(204)
    }

    @ParameterizedTest
    @ValueSource(strings = ["MUSLIM", "ATHEIST","AGNOSTIC"])
    @DisplayName("Should change belief endpoint")
    fun shouldChangeBelief(belief:String){
        val detailsReference = "123456789"
        val csrfToken = "X12Z78S45e89"
        doNothing().`when`(changeDetailsIn).changeBeliefs(detailsReference, Beliefs.valueOf(belief))
        given()
            .header("Content-Type", "application/json")
            .cookie("Bearer", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpY2kiLCJ1cG4iOiI4YTg3MGJmOGQzODY0NzVkODhlOTFmOTg3MjBmNWVmZSIsImdyb3VwcyI6WyJQUkVUTkRFUiJdLCJleHAiOjE3MzMxMTE3MDYsImFkZHJlc3MiOiJwZXRwYWxzLWFwcHMiLCJlbWFpbCI6InNhLmJlbm5hY2V1ckBnbWFpbC5jb20iLCJwaG9uZV9udW1iZXIiOiIwNzY0MDE3NTI4IiwiaWF0IjoxNzMxOTAyMTA2LCJqdGkiOiJhZGZiZmNmNy0xNjUyLTRkZGEtYmU0Yi1mY2M0NDY1MjhkNWMifQ.N7An4PPtbvMWzavCWycTdIQe1k7MTnDX7QUoGfvLTfbQA3Je7IqfDPb3Hz4QS7ZF72qQsIQ56bnDeSv5egxziEsmyK7wY8OCAZ7AV6YGNw2C75-ZCHC4rTh77kyQ0Jfx3YACytV20RGDxxCCsUhinJh706lOB67QYvJLf6L-hBeTq-i5-GZ6u_O1-iBTD3pQR2VzQ3rFMn8_Mj5Ve2fLlR2oh2AGtRhfZOcL4rfi_BH-G4UeY89N_Dkh23sL6o9B6HFqoO3T1Z4Z2gi_3qWhIA3cZ0GQmE4HA3SMnx5ksDsAX5i0lO6bLnJRhoIcR-nfvxmTksu3Sy-qAuw-cdqejQ")
            .cookie("x-csrf-token", csrfToken)
            .header("x-csrf-token", csrfToken)
            .`when`()
            .put("/pretnder-profile/beliefs/$detailsReference/$belief")
            .then()
            .statusCode(204)
    }

    @ParameterizedTest
    @ValueSource(strings = ["ONCE_IN_A_WHILE", "NEVER","OFTEN"])
    @DisplayName("Should change smokes occurrence endpoint")
    fun shouldChangeSmokesOccurrence(smokes:String){
        val detailsReference = "123456789"
        val csrfToken = "X12Z78S45e89"
        doNothing().`when`(changeDetailsIn).changeSmokes(detailsReference, Occurrences.valueOf(smokes))
        given()
            .header("Content-Type", "application/json")
            .cookie("Bearer", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpY2kiLCJ1cG4iOiI4YTg3MGJmOGQzODY0NzVkODhlOTFmOTg3MjBmNWVmZSIsImdyb3VwcyI6WyJQUkVUTkRFUiJdLCJleHAiOjE3MzMxMTE3MDYsImFkZHJlc3MiOiJwZXRwYWxzLWFwcHMiLCJlbWFpbCI6InNhLmJlbm5hY2V1ckBnbWFpbC5jb20iLCJwaG9uZV9udW1iZXIiOiIwNzY0MDE3NTI4IiwiaWF0IjoxNzMxOTAyMTA2LCJqdGkiOiJhZGZiZmNmNy0xNjUyLTRkZGEtYmU0Yi1mY2M0NDY1MjhkNWMifQ.N7An4PPtbvMWzavCWycTdIQe1k7MTnDX7QUoGfvLTfbQA3Je7IqfDPb3Hz4QS7ZF72qQsIQ56bnDeSv5egxziEsmyK7wY8OCAZ7AV6YGNw2C75-ZCHC4rTh77kyQ0Jfx3YACytV20RGDxxCCsUhinJh706lOB67QYvJLf6L-hBeTq-i5-GZ6u_O1-iBTD3pQR2VzQ3rFMn8_Mj5Ve2fLlR2oh2AGtRhfZOcL4rfi_BH-G4UeY89N_Dkh23sL6o9B6HFqoO3T1Z4Z2gi_3qWhIA3cZ0GQmE4HA3SMnx5ksDsAX5i0lO6bLnJRhoIcR-nfvxmTksu3Sy-qAuw-cdqejQ")
            .cookie("x-csrf-token", csrfToken)
            .header("x-csrf-token", csrfToken)
            .`when`()
            .put("/pretnder-profile/smokes/$detailsReference/$smokes")
            .then()
            .statusCode(204)
    }

    @ParameterizedTest
    @ValueSource(strings = ["ONCE_IN_A_WHILE", "NEVER","OFTEN"])
    @DisplayName("Should change drinks occurrence endpoint")
    fun shouldChangeDrinksOccurrence(drinks:String){
        val detailsReference = "123456789"
        val csrfToken = "X12Z78S45e89"
        doNothing().`when`(changeDetailsIn).changeDrinks(detailsReference, Occurrences.valueOf(drinks))
        given()
            .header("Content-Type", "application/json")
            .cookie("Bearer", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpY2kiLCJ1cG4iOiI4YTg3MGJmOGQzODY0NzVkODhlOTFmOTg3MjBmNWVmZSIsImdyb3VwcyI6WyJQUkVUTkRFUiJdLCJleHAiOjE3MzMxMTE3MDYsImFkZHJlc3MiOiJwZXRwYWxzLWFwcHMiLCJlbWFpbCI6InNhLmJlbm5hY2V1ckBnbWFpbC5jb20iLCJwaG9uZV9udW1iZXIiOiIwNzY0MDE3NTI4IiwiaWF0IjoxNzMxOTAyMTA2LCJqdGkiOiJhZGZiZmNmNy0xNjUyLTRkZGEtYmU0Yi1mY2M0NDY1MjhkNWMifQ.N7An4PPtbvMWzavCWycTdIQe1k7MTnDX7QUoGfvLTfbQA3Je7IqfDPb3Hz4QS7ZF72qQsIQ56bnDeSv5egxziEsmyK7wY8OCAZ7AV6YGNw2C75-ZCHC4rTh77kyQ0Jfx3YACytV20RGDxxCCsUhinJh706lOB67QYvJLf6L-hBeTq-i5-GZ6u_O1-iBTD3pQR2VzQ3rFMn8_Mj5Ve2fLlR2oh2AGtRhfZOcL4rfi_BH-G4UeY89N_Dkh23sL6o9B6HFqoO3T1Z4Z2gi_3qWhIA3cZ0GQmE4HA3SMnx5ksDsAX5i0lO6bLnJRhoIcR-nfvxmTksu3Sy-qAuw-cdqejQ")
            .cookie("x-csrf-token", csrfToken)
            .header("x-csrf-token", csrfToken)
            .`when`()
            .put("/pretnder-profile/drinks/$detailsReference/$drinks")
            .then()
            .statusCode(204)
    }


    @ParameterizedTest
    @ValueSource(strings = ["STUDENT", "EMPLOYED","INACTIVE"])
    @DisplayName("Should change social status endpoint")
    fun shouldChangeSocialStatus(socialStatus:String){
        val detailsReference = "123456789"
        val csrfToken = "X12Z78S45e89"
        doNothing().`when`(changeDetailsIn).changeSocialStatus(detailsReference, SocialStatus.valueOf(socialStatus))
        given()
            .header("Content-Type", "application/json")
            .cookie("Bearer", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpY2kiLCJ1cG4iOiI4YTg3MGJmOGQzODY0NzVkODhlOTFmOTg3MjBmNWVmZSIsImdyb3VwcyI6WyJQUkVUTkRFUiJdLCJleHAiOjE3MzMxMTE3MDYsImFkZHJlc3MiOiJwZXRwYWxzLWFwcHMiLCJlbWFpbCI6InNhLmJlbm5hY2V1ckBnbWFpbC5jb20iLCJwaG9uZV9udW1iZXIiOiIwNzY0MDE3NTI4IiwiaWF0IjoxNzMxOTAyMTA2LCJqdGkiOiJhZGZiZmNmNy0xNjUyLTRkZGEtYmU0Yi1mY2M0NDY1MjhkNWMifQ.N7An4PPtbvMWzavCWycTdIQe1k7MTnDX7QUoGfvLTfbQA3Je7IqfDPb3Hz4QS7ZF72qQsIQ56bnDeSv5egxziEsmyK7wY8OCAZ7AV6YGNw2C75-ZCHC4rTh77kyQ0Jfx3YACytV20RGDxxCCsUhinJh706lOB67QYvJLf6L-hBeTq-i5-GZ6u_O1-iBTD3pQR2VzQ3rFMn8_Mj5Ve2fLlR2oh2AGtRhfZOcL4rfi_BH-G4UeY89N_Dkh23sL6o9B6HFqoO3T1Z4Z2gi_3qWhIA3cZ0GQmE4HA3SMnx5ksDsAX5i0lO6bLnJRhoIcR-nfvxmTksu3Sy-qAuw-cdqejQ")
            .cookie("x-csrf-token", csrfToken)
            .header("x-csrf-token", csrfToken)
            .`when`()
            .put("/pretnder-profile/social-status/$detailsReference/$socialStatus")
            .then()
            .statusCode(204)
    }

    @ParameterizedTest
    @ValueSource(strings = ["CISMALE", "NONBINARY","GENDERFLUID","ANDROGYNE"])
    @DisplayName("Should change social gender endpoint")
    fun shouldChangeGender(gender:String){
        val detailsReference = "123456789"
        val csrfToken = "X12Z78S45e89"
        doNothing().`when`(changeDetailsIn).changeGender(detailsReference, Genders.valueOf(gender))
        given()
            .header("Content-Type", "application/json")
            .cookie("Bearer", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpY2kiLCJ1cG4iOiI4YTg3MGJmOGQzODY0NzVkODhlOTFmOTg3MjBmNWVmZSIsImdyb3VwcyI6WyJQUkVUTkRFUiJdLCJleHAiOjE3MzMxMTE3MDYsImFkZHJlc3MiOiJwZXRwYWxzLWFwcHMiLCJlbWFpbCI6InNhLmJlbm5hY2V1ckBnbWFpbC5jb20iLCJwaG9uZV9udW1iZXIiOiIwNzY0MDE3NTI4IiwiaWF0IjoxNzMxOTAyMTA2LCJqdGkiOiJhZGZiZmNmNy0xNjUyLTRkZGEtYmU0Yi1mY2M0NDY1MjhkNWMifQ.N7An4PPtbvMWzavCWycTdIQe1k7MTnDX7QUoGfvLTfbQA3Je7IqfDPb3Hz4QS7ZF72qQsIQ56bnDeSv5egxziEsmyK7wY8OCAZ7AV6YGNw2C75-ZCHC4rTh77kyQ0Jfx3YACytV20RGDxxCCsUhinJh706lOB67QYvJLf6L-hBeTq-i5-GZ6u_O1-iBTD3pQR2VzQ3rFMn8_Mj5Ve2fLlR2oh2AGtRhfZOcL4rfi_BH-G4UeY89N_Dkh23sL6o9B6HFqoO3T1Z4Z2gi_3qWhIA3cZ0GQmE4HA3SMnx5ksDsAX5i0lO6bLnJRhoIcR-nfvxmTksu3Sy-qAuw-cdqejQ")
            .cookie("x-csrf-token", csrfToken)
            .header("x-csrf-token", csrfToken)
            .`when`()
            .put("/pretnder-profile/gender/$detailsReference/$gender")
            .then()
            .statusCode(204)
    }


    @ParameterizedTest
    @ValueSource(strings = ["HETEROSEXUAL", "HOMOSEXUAL","HOMOFLEXIBLE"])
    @DisplayName("Should change orientation endpoint")
    fun shouldChangeOrientation(orientation:String){
        val detailsReference = "123456789"
        val csrfToken = "X12Z78S45e89"
        doNothing().`when`(changeDetailsIn).changeOrientation(detailsReference, SexualOrientations.valueOf(orientation))
        given()
            .header("Content-Type", "application/json")
            .cookie("Bearer", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpY2kiLCJ1cG4iOiI4YTg3MGJmOGQzODY0NzVkODhlOTFmOTg3MjBmNWVmZSIsImdyb3VwcyI6WyJQUkVUTkRFUiJdLCJleHAiOjE3MzMxMTE3MDYsImFkZHJlc3MiOiJwZXRwYWxzLWFwcHMiLCJlbWFpbCI6InNhLmJlbm5hY2V1ckBnbWFpbC5jb20iLCJwaG9uZV9udW1iZXIiOiIwNzY0MDE3NTI4IiwiaWF0IjoxNzMxOTAyMTA2LCJqdGkiOiJhZGZiZmNmNy0xNjUyLTRkZGEtYmU0Yi1mY2M0NDY1MjhkNWMifQ.N7An4PPtbvMWzavCWycTdIQe1k7MTnDX7QUoGfvLTfbQA3Je7IqfDPb3Hz4QS7ZF72qQsIQ56bnDeSv5egxziEsmyK7wY8OCAZ7AV6YGNw2C75-ZCHC4rTh77kyQ0Jfx3YACytV20RGDxxCCsUhinJh706lOB67QYvJLf6L-hBeTq-i5-GZ6u_O1-iBTD3pQR2VzQ3rFMn8_Mj5Ve2fLlR2oh2AGtRhfZOcL4rfi_BH-G4UeY89N_Dkh23sL6o9B6HFqoO3T1Z4Z2gi_3qWhIA3cZ0GQmE4HA3SMnx5ksDsAX5i0lO6bLnJRhoIcR-nfvxmTksu3Sy-qAuw-cdqejQ")
            .cookie("x-csrf-token", csrfToken)
            .header("x-csrf-token", csrfToken)
            .`when`()
            .put("/pretnder-profile/orientation/$detailsReference/$orientation")
            .then()
            .statusCode(204)
    }


    @ParameterizedTest
    @ValueSource(strings = ["Paris", "Lille","Lyon"])
    @DisplayName("Should change city endpoint")
    fun shouldChangeCity(city:String){
        val detailsReference = "123456789"
        val csrfToken = "X12Z78S45e89"
        doNothing().`when`(changeDetailsIn).changeCity(detailsReference, city)
        given()
            .header("Content-Type", "application/json")
            .cookie("Bearer", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpY2kiLCJ1cG4iOiI4YTg3MGJmOGQzODY0NzVkODhlOTFmOTg3MjBmNWVmZSIsImdyb3VwcyI6WyJQUkVUTkRFUiJdLCJleHAiOjE3MzMxMTE3MDYsImFkZHJlc3MiOiJwZXRwYWxzLWFwcHMiLCJlbWFpbCI6InNhLmJlbm5hY2V1ckBnbWFpbC5jb20iLCJwaG9uZV9udW1iZXIiOiIwNzY0MDE3NTI4IiwiaWF0IjoxNzMxOTAyMTA2LCJqdGkiOiJhZGZiZmNmNy0xNjUyLTRkZGEtYmU0Yi1mY2M0NDY1MjhkNWMifQ.N7An4PPtbvMWzavCWycTdIQe1k7MTnDX7QUoGfvLTfbQA3Je7IqfDPb3Hz4QS7ZF72qQsIQ56bnDeSv5egxziEsmyK7wY8OCAZ7AV6YGNw2C75-ZCHC4rTh77kyQ0Jfx3YACytV20RGDxxCCsUhinJh706lOB67QYvJLf6L-hBeTq-i5-GZ6u_O1-iBTD3pQR2VzQ3rFMn8_Mj5Ve2fLlR2oh2AGtRhfZOcL4rfi_BH-G4UeY89N_Dkh23sL6o9B6HFqoO3T1Z4Z2gi_3qWhIA3cZ0GQmE4HA3SMnx5ksDsAX5i0lO6bLnJRhoIcR-nfvxmTksu3Sy-qAuw-cdqejQ")
            .cookie("x-csrf-token", csrfToken)
            .header("x-csrf-token", csrfToken)
            .`when`()
            .put("/pretnder-profile/city/$detailsReference/$city")
            .then()
            .statusCode(204)
    }

    @ParameterizedTest
    @ValueSource(strings = ["France", "Algérie","Espagne"])
    @DisplayName("Should change city endpoint")
    fun shouldChangeCountry(country:String){
        val detailsReference = "123456789"
        val csrfToken = "X12Z78S45e89"
        doNothing().`when`(changeDetailsIn).changeCountry(detailsReference, country)
        given()
            .header("Content-Type", "application/json")
            .cookie("Bearer", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpY2kiLCJ1cG4iOiI4YTg3MGJmOGQzODY0NzVkODhlOTFmOTg3MjBmNWVmZSIsImdyb3VwcyI6WyJQUkVUTkRFUiJdLCJleHAiOjE3MzMxMTE3MDYsImFkZHJlc3MiOiJwZXRwYWxzLWFwcHMiLCJlbWFpbCI6InNhLmJlbm5hY2V1ckBnbWFpbC5jb20iLCJwaG9uZV9udW1iZXIiOiIwNzY0MDE3NTI4IiwiaWF0IjoxNzMxOTAyMTA2LCJqdGkiOiJhZGZiZmNmNy0xNjUyLTRkZGEtYmU0Yi1mY2M0NDY1MjhkNWMifQ.N7An4PPtbvMWzavCWycTdIQe1k7MTnDX7QUoGfvLTfbQA3Je7IqfDPb3Hz4QS7ZF72qQsIQ56bnDeSv5egxziEsmyK7wY8OCAZ7AV6YGNw2C75-ZCHC4rTh77kyQ0Jfx3YACytV20RGDxxCCsUhinJh706lOB67QYvJLf6L-hBeTq-i5-GZ6u_O1-iBTD3pQR2VzQ3rFMn8_Mj5Ve2fLlR2oh2AGtRhfZOcL4rfi_BH-G4UeY89N_Dkh23sL6o9B6HFqoO3T1Z4Z2gi_3qWhIA3cZ0GQmE4HA3SMnx5ksDsAX5i0lO6bLnJRhoIcR-nfvxmTksu3Sy-qAuw-cdqejQ")
            .cookie("x-csrf-token", csrfToken)
            .header("x-csrf-token", csrfToken)
            .`when`()
            .put("/pretnder-profile/country/$detailsReference/$country")
            .then()
            .statusCode(204)
    }

    @Test
    @DisplayName("Should change biography endpoint")
    fun shouldChangeBiography(){
        val detailsReference = "123456789"
        val csrfToken = "X12Z78S45e89"
        val biography = "Salut\nJe m'appelle sid et je vous emmerde\net je vous encule"
        val request = ChangeBiographyRequest(
            detailsReference,
            biography
        )
        val mapper = ObjectMapper()
        val jsonRequest = mapper.writeValueAsString(request)
        doNothing().`when`(changeDetailsIn).changeBiography(detailsReference, biography)
        given()
            .header("Content-Type", "application/json")
            .cookie("Bearer", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpY2kiLCJ1cG4iOiI4YTg3MGJmOGQzODY0NzVkODhlOTFmOTg3MjBmNWVmZSIsImdyb3VwcyI6WyJQUkVUTkRFUiJdLCJleHAiOjE3MzMxMTE3MDYsImFkZHJlc3MiOiJwZXRwYWxzLWFwcHMiLCJlbWFpbCI6InNhLmJlbm5hY2V1ckBnbWFpbC5jb20iLCJwaG9uZV9udW1iZXIiOiIwNzY0MDE3NTI4IiwiaWF0IjoxNzMxOTAyMTA2LCJqdGkiOiJhZGZiZmNmNy0xNjUyLTRkZGEtYmU0Yi1mY2M0NDY1MjhkNWMifQ.N7An4PPtbvMWzavCWycTdIQe1k7MTnDX7QUoGfvLTfbQA3Je7IqfDPb3Hz4QS7ZF72qQsIQ56bnDeSv5egxziEsmyK7wY8OCAZ7AV6YGNw2C75-ZCHC4rTh77kyQ0Jfx3YACytV20RGDxxCCsUhinJh706lOB67QYvJLf6L-hBeTq-i5-GZ6u_O1-iBTD3pQR2VzQ3rFMn8_Mj5Ve2fLlR2oh2AGtRhfZOcL4rfi_BH-G4UeY89N_Dkh23sL6o9B6HFqoO3T1Z4Z2gi_3qWhIA3cZ0GQmE4HA3SMnx5ksDsAX5i0lO6bLnJRhoIcR-nfvxmTksu3Sy-qAuw-cdqejQ")
            .cookie("x-csrf-token", csrfToken)
            .header("x-csrf-token", csrfToken)
            .body(jsonRequest)
            .`when`()
            .put("/pretnder-profile/biography")
            .then()
            .statusCode(204)
    }


    @Test
    @DisplayName("Should change nickname endpoint")
    fun shouldChangeNickname(){
        val newNickname = "Sidoudou"
        val csrfToken = "X12Z78S45e89"
        val request = ChangeNicknameRequest(
            newNickname
        )
        val mapper = ObjectMapper()
        val jsonRequest = mapper.writeValueAsString(request)
        doNothing().`when`(changeDetailsIn).changeNickname(any(), eq(request.newNickname))
        given()
            .header("Content-Type", "application/json")
            .cookie("Bearer", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpY2kiLCJ1cG4iOiI4YTg3MGJmOGQzODY0NzVkODhlOTFmOTg3MjBmNWVmZSIsImdyb3VwcyI6WyJQUkVUTkRFUiJdLCJleHAiOjE3MzMxMTE3MDYsImFkZHJlc3MiOiJwZXRwYWxzLWFwcHMiLCJlbWFpbCI6InNhLmJlbm5hY2V1ckBnbWFpbC5jb20iLCJwaG9uZV9udW1iZXIiOiIwNzY0MDE3NTI4IiwiaWF0IjoxNzMxOTAyMTA2LCJqdGkiOiJhZGZiZmNmNy0xNjUyLTRkZGEtYmU0Yi1mY2M0NDY1MjhkNWMifQ.N7An4PPtbvMWzavCWycTdIQe1k7MTnDX7QUoGfvLTfbQA3Je7IqfDPb3Hz4QS7ZF72qQsIQ56bnDeSv5egxziEsmyK7wY8OCAZ7AV6YGNw2C75-ZCHC4rTh77kyQ0Jfx3YACytV20RGDxxCCsUhinJh706lOB67QYvJLf6L-hBeTq-i5-GZ6u_O1-iBTD3pQR2VzQ3rFMn8_Mj5Ve2fLlR2oh2AGtRhfZOcL4rfi_BH-G4UeY89N_Dkh23sL6o9B6HFqoO3T1Z4Z2gi_3qWhIA3cZ0GQmE4HA3SMnx5ksDsAX5i0lO6bLnJRhoIcR-nfvxmTksu3Sy-qAuw-cdqejQ")
            .cookie("x-csrf-token", csrfToken)
            .header("x-csrf-token", csrfToken)
            .body(jsonRequest)
            .`when`()
            .put("/pretnder-profile/nickname")
            .then()
            .statusCode(204)
    }

    @Test
    @DisplayName("Should change height endpoint")
    fun shouldChangeHeight(){
        val detailsReference = "123456789"
        val newHeight = "185"
        val csrfToken = "X12Z78S45e89"
        doNothing().`when`(changeDetailsIn).changeHeight(detailsReference, newHeight)
        given()
            .header("Content-Type", "application/json")
            .cookie("Bearer", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJpY2kiLCJ1cG4iOiI4YTg3MGJmOGQzODY0NzVkODhlOTFmOTg3MjBmNWVmZSIsImdyb3VwcyI6WyJQUkVUTkRFUiJdLCJleHAiOjE3MzMxMTE3MDYsImFkZHJlc3MiOiJwZXRwYWxzLWFwcHMiLCJlbWFpbCI6InNhLmJlbm5hY2V1ckBnbWFpbC5jb20iLCJwaG9uZV9udW1iZXIiOiIwNzY0MDE3NTI4IiwiaWF0IjoxNzMxOTAyMTA2LCJqdGkiOiJhZGZiZmNmNy0xNjUyLTRkZGEtYmU0Yi1mY2M0NDY1MjhkNWMifQ.N7An4PPtbvMWzavCWycTdIQe1k7MTnDX7QUoGfvLTfbQA3Je7IqfDPb3Hz4QS7ZF72qQsIQ56bnDeSv5egxziEsmyK7wY8OCAZ7AV6YGNw2C75-ZCHC4rTh77kyQ0Jfx3YACytV20RGDxxCCsUhinJh706lOB67QYvJLf6L-hBeTq-i5-GZ6u_O1-iBTD3pQR2VzQ3rFMn8_Mj5Ve2fLlR2oh2AGtRhfZOcL4rfi_BH-G4UeY89N_Dkh23sL6o9B6HFqoO3T1Z4Z2gi_3qWhIA3cZ0GQmE4HA3SMnx5ksDsAX5i0lO6bLnJRhoIcR-nfvxmTksu3Sy-qAuw-cdqejQ")
            .cookie("x-csrf-token", csrfToken)
            .header("x-csrf-token", csrfToken)
            .`when`()
            .put("/pretnder-profile/height/$detailsReference/$newHeight")
            .then()
            .statusCode(204)
    }
}