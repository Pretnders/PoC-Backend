package pretnders.api.profile_pictures.application

import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.logging.Log
import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import jakarta.inject.Inject
import jakarta.ws.rs.core.MediaType
import org.hamcrest.Matchers.equalTo
import org.jboss.resteasy.reactive.multipart.FileUpload
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.*
import pretnders.api.profile_pictures.application.dto.requests.DeletePictureRequest
import pretnders.api.profile_pictures.application.dto.requests.SwapPicturesRequest
import pretnders.api.profile_pictures.application.dto.responses.AddProfilePictureResponse
import pretnders.api.profile_pictures.domain.models.AddedProfilePicture
import pretnders.api.profile_pictures.domain.ports.`in`.ChangeProfilePictureCommand
import pretnders.api.profile_pictures.domain.ports.`in`.HandleProfilePicturesIn
import pretnders.api.profile_pictures.domain.ports.`in`.RemoveProfilePictureCommand
import pretnders.api.profile_pictures.domain.ports.`in`.SwapProfilePictureCommand
import pretnders.api.shared.security.JwtTokenGenerator
import pretnders.api.shared.utils.shared_models.UserTypes
import java.io.File

@QuarkusTest
@DisplayName("Profile pictures endpoint tests")
class ProfilePicturesEndpointTest {

    @InjectMock
    private lateinit var handleProfilePicturesIn: HandleProfilePicturesIn

    @Inject
    private lateinit var jsonTokenGenerator: JwtTokenGenerator

    companion object {
        const val CSRF_TOKEN = "123456"
        const val URL = "https://some-url.com/file.png"
        val file = File("src/test/resources/cuteanimegirl.png")
        private const val MAIL = "sa.bennaceur@test.com"
        private const val REFERENCE = "123456789"
        const val PICTURE_REFERENCE = "PICREFERENCE"
        private const val PHONE_NUMBER = "0712121212"
        lateinit var token: String
        private val mapper = ObjectMapper()
    }

    @BeforeEach
    fun ini() {
        token = jsonTokenGenerator.getToken(REFERENCE, PHONE_NUMBER, MAIL, UserTypes.PRETNDER.name)
    }

    @Test
    fun shouldAddProfilePicture() {
        val addProfilePictureResponse = AddedProfilePicture(
            PICTURE_REFERENCE,
            URL
        )
        val stringCaptor = argumentCaptor<String>()
        val fileCaptor = argumentCaptor<FileUpload>()

        whenever(handleProfilePicturesIn.addProfilePicture(eq(REFERENCE), eq(PHONE_NUMBER), any()))
            .thenReturn(addProfilePictureResponse)
        val res = RestAssured.given()
            .cookie("Bearer", token)
            .header("x-csrf-token", CSRF_TOKEN)
            .cookie("x-csrf-token", CSRF_TOKEN)
            .contentType("multipart/form-data")
            .multiPart("image", file)
            .`when`()
            .post("/profile-pictures/pretnders")
            .then().extract()

        val responseBody = res.body().`as`(AddProfilePictureResponse::class.java)

        verify(handleProfilePicturesIn).addProfilePicture(
            stringCaptor.capture(),
            stringCaptor.capture(),
            fileCaptor.capture()
        )
        assert(201 == res.statusCode())
        assert(PICTURE_REFERENCE == responseBody.reference)
        assert(URL == responseBody.url)
        assert(REFERENCE == stringCaptor.firstValue)
        assert(PHONE_NUMBER == stringCaptor.secondValue)
        assert(file.name == fileCaptor.firstValue.fileName())
    }


    @Test
    fun shouldDeleteProfilePicture() {
        val removePictureRequest = DeletePictureRequest(
            PICTURE_REFERENCE,
            file.name
        )
        val commandCaptor = argumentCaptor<RemoveProfilePictureCommand>()
        val jsonRequest: String = mapper.writeValueAsString(removePictureRequest)

        val command = RemoveProfilePictureCommand(
            removePictureRequest.reference,
            removePictureRequest.blobName,
            PHONE_NUMBER
        )

        doNothing().`when`(handleProfilePicturesIn).removeProfilePicture(command)
        val res = RestAssured.given()
            .cookie("Bearer", token)
            .header("x-csrf-token", CSRF_TOKEN)
            .cookie("x-csrf-token", CSRF_TOKEN)
            .contentType(MediaType.APPLICATION_JSON)
            .body(jsonRequest)
            .`when`()
            .delete("/profile-pictures/pretnders")
            .then().extract()


        verify(handleProfilePicturesIn).removeProfilePicture(commandCaptor.capture())
        assert(204 == res.statusCode())
        assert(PICTURE_REFERENCE == commandCaptor.firstValue.profilePictureReference)
        assert(removePictureRequest.blobName == commandCaptor.firstValue.blobName)
        assert(PHONE_NUMBER == commandCaptor.firstValue.phoneNumber)
    }

    @Test
    fun shouldSwapProfilePictures() {
        val swapperOrder = 5L
        val swappedOrder = 5L
        val swapperReference = "123456789"
        val swappedReference = "987654321"
        val swapPicturesRequest = SwapPicturesRequest(
            swapperReference,
            swapperOrder,
            swappedReference,
            swappedOrder
        )
        val commandCaptor = argumentCaptor<SwapProfilePictureCommand>()
        val jsonRequest: String = mapper.writeValueAsString(swapPicturesRequest)

        val command = SwapProfilePictureCommand(
            swapPicturesRequest.swapperReference,
            swapPicturesRequest.swapperOrder,
            swapPicturesRequest.swappedReference,
            swapPicturesRequest.swappedOrder,
        )

        doNothing().`when`(handleProfilePicturesIn).swapPicturesOrder(command)
        val res = RestAssured.given()
            .cookie("Bearer", token)
            .header("x-csrf-token", CSRF_TOKEN)
            .cookie("x-csrf-token", CSRF_TOKEN)
            .contentType(MediaType.APPLICATION_JSON)
            .body(jsonRequest)
            .`when`()
            .put("/profile-pictures/pretnders")
            .then().extract()


        verify(handleProfilePicturesIn).swapPicturesOrder(commandCaptor.capture())
        assert(204 == res.statusCode())
        assert(swapperReference == commandCaptor.firstValue.swapperReference)
        assert(swapperOrder == commandCaptor.firstValue.swapperOrder)
        assert(swappedReference == commandCaptor.firstValue.swappedReference)
        assert(swapperOrder == commandCaptor.firstValue.swapperOrder)
    }

    @Test
    fun shouldChangeProfilePicture() {
        whenever(handleProfilePicturesIn.changeProfilePicture(any())).thenReturn(URL)
        RestAssured.given()
            .cookie("Bearer", token)
            .header("x-csrf-token", CSRF_TOKEN)
            .cookie("x-csrf-token", CSRF_TOKEN)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .multiPart("image", file)
            .`when`()
            .put("/profile-pictures/pretnders/change-picture/$PICTURE_REFERENCE/${file.name}")
            .then().statusCode(200).body(equalTo(URL))

        verify(handleProfilePicturesIn).changeProfilePicture(any())
    }
}