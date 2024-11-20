package pretnders.api.profile_pictures.persistence.repositories

import io.quarkus.test.TestTransaction
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

@QuarkusTest
@DisplayName("Profile pics commands repository Unit Tests")
class ProfilePicsCommandsRepositoryUnitTest {
    @Inject
    private lateinit var picsCommandsRepository: ProfilePicsCommandsRepository

    @Test
    @TestTransaction
    fun shouldSwapOrders(){
        val swapperReference = "8b76facfa0804a67b7ae9054597a7504"
        val swappedReference = "29b411370ae548ac835285daa891418b"
        val swapperOrder:Short = 1
        val swappedOrder:Short = 0
        assertDoesNotThrow {
            picsCommandsRepository.swapPicturesOrder(swapperReference,swapperOrder,swappedReference,swappedOrder)
        }
        val swapperInDb = picsCommandsRepository.find("reference", swapperReference).firstResult()!!
        assert(swappedOrder == swapperInDb.order)
        val swappedInDb = picsCommandsRepository.find("reference", swappedReference).firstResult()!!
        assert(swapperOrder == swappedInDb.order)
    }

    @Test
    @TestTransaction
    fun shouldChangeUrl(){
        val profilePicReference = "8b76facfa0804a67b7ae9054597a7504"

        val newUrl = "some-url"
        assertDoesNotThrow {
            picsCommandsRepository.changePictureUrl(profilePicReference,newUrl)
        }
        val swapperInDb = picsCommandsRepository.find("reference", profilePicReference).firstResult()!!
        assert(newUrl == swapperInDb.url)
    }

    @Test
    @TestTransaction
    fun shouldChangeOrderByID(){
        val newOrder:Short = 6

        val id = 26L
        assertDoesNotThrow {
            picsCommandsRepository.setPictureOrderById(newOrder,id)
        }
        val swapperInDb = picsCommandsRepository.findById(id)!!
        assert(newOrder == swapperInDb.order)
    }
}