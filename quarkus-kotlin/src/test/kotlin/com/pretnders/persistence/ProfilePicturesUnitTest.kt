package com.pretnders.persistence

import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.utils.UUIDGenerator.getNewUUID
import com.pretnders.persistence.repositories.ProfilePicsRepository
import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

val reference = getNewUUID()

@QuarkusTest
@DisplayName("Tests profile pictures functionalities")
class ProfilePicturesUnitTest {

    @InjectMock
    private lateinit var profilePicsRepository: ProfilePicsRepository

    @ParameterizedTest(
        name = "Should return the correct picture order"
    )
    @DisplayName("Should return the correct picture order")
    @ValueSource(shorts = [1,0,8,2])
    @Order(1)
    fun shouldRetrieveValidPicOrder(returnValue:Short) {
        whenever(profilePicsRepository.findNextPicOrder(reference)).doReturn(returnValue)
        assert(profilePicsRepository.findNextPicOrder(reference) == returnValue)
    }

    @ParameterizedTest(
        name = "Should throw error, too many pictures already registered"
    )
    @DisplayName("Should throw error, too many pictures already registered")
    @ValueSource(shorts = [Short.MIN_VALUE, 9,12,100,Short.MAX_VALUE])
    @Order(1)
    fun shouldThrowErrorCauseTooManyPictures(returnValue:Short) {
        whenever(profilePicsRepository.findNextPicOrder(reference)).thenThrow(ApplicationException(ApplicationExceptionsEnum.PICTURE_ORDER_OUT_OF_BOUND))
        assertThrows<ApplicationException> { profilePicsRepository.findNextPicOrder(reference) }
    }

}

