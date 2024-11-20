package pretnders.api.pretnders.persistence

import io.quarkus.test.TestTransaction
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import pretnders.api.pretnders.persistence.repositories.PretnderTraitPairsCommandsRepository
import pretnders.api.shared.errors.ApplicationException

@QuarkusTest
class PretnderTraitPairsCommandsRepositoryUnitTest {

    @Inject
    private lateinit var pretnderTraitPairsCommandsRepository: PretnderTraitPairsCommandsRepository
    companion object {
        const val TRAIT_PAIR_REFERENCE = "b14fc0f0a7df4cf2942ccdb96e5538b5"
    }

    @ParameterizedTest
     @ValueSource(shorts = [0,1, 2, 3, 4, 5, 6, 7, 8, 9, 10,25,30,42,50,58,85,89])
    @TestTransaction
    fun shouldUpdateScore(score:Short){
        assertDoesNotThrow {
            pretnderTraitPairsCommandsRepository.updateScore(TRAIT_PAIR_REFERENCE, score)
        }
        val inDb = pretnderTraitPairsCommandsRepository.find("reference", TRAIT_PAIR_REFERENCE).firstResult()!!
        assert(score == inDb.score)
    }

    @ParameterizedTest
    @ValueSource(shorts = [-10, -5, Short.MAX_VALUE, Short.MIN_VALUE, 250])
    @TestTransaction
    fun shouldThrowCheckViolation(score:Short){
        assertThrows<ApplicationException> {
            pretnderTraitPairsCommandsRepository.updateScore(TRAIT_PAIR_REFERENCE, score)
        }
    }
}