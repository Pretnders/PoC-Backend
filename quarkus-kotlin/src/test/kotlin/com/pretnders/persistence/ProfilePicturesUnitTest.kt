package com.pretnders.persistence

import com.pretnders.shared.errors.ApplicationException
import com.pretnders.shared.errors.ApplicationExceptionsEnum
import com.pretnders.profile_pictures.persistence.repositories.ProfilePicsRepository
import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

val pretnderID = 1L

@QuarkusTest
@DisplayName("Tests profile pictures functionalities")
class ProfilePicturesUnitTest {

    @InjectMock
    private lateinit var profilePicsRepository: ProfilePicsRepository

    @ParameterizedTest(
        name = "Should return the correct picture order"
    )
    @DisplayName("Should return the correct picture order")
    @ValueSource(longs = [1,0,8,2])
    @Order(1)
    fun shouldRetrieveValidPicOrder(returnValue:Long) {
        whenever(profilePicsRepository.findNextPicOrder(pretnderID)).doReturn(returnValue)
        assert(profilePicsRepository.findNextPicOrder(pretnderID) == returnValue)
    }

    @ParameterizedTest(
        name = "Should throw error, too many pictures already registered"
    )
    @DisplayName("Should throw error, too many pictures already registered")
    @ValueSource(longs = [Long.MIN_VALUE, 9,12,100,Long.MAX_VALUE])
    @Order(1)
    fun shouldThrowErrorCauseTooManyPictures(returnValue:Long) {
        whenever(profilePicsRepository.findNextPicOrder(pretnderID)).thenThrow(
            ApplicationException(
                ApplicationExceptionsEnum.PICTURE_ORDER_OUT_OF_BOUND)
        )
        assertThrows<ApplicationException> { profilePicsRepository.findNextPicOrder(pretnderID) }
    }

}

