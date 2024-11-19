package pretnders.api.pretnders.domain

import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import pretnders.api.pretnders.domain.ports.`in`.ChangeTraitScoreIn
import pretnders.api.pretnders.domain.ports.out.ChangeTraitScoreOut
import pretnders.api.shared.errors.ApplicationException

@QuarkusTest
@DisplayName("Domain pretnder trait score unit tests")
class ChangeTraitPairScoreUnitTest {
    @Inject
    private lateinit var changeTraitScoreIn: ChangeTraitScoreIn

    @InjectMock
    private lateinit var changeTraitScoreOut: ChangeTraitScoreOut

    private val traitPairReference = "123456789"

    @ParameterizedTest
    @ValueSource(shorts = [0, 12, 25,75,99, 100])
    @DisplayName("Should update trait score by trait reference")
    fun shouldUpdateTraitScore(newScore:Short){
        val referenceCaptor = argumentCaptor<String>()
        val scoreCaptor = argumentCaptor<Short>()

        doNothing().`when`(changeTraitScoreOut).changeScore(traitPairReference, newScore)
        changeTraitScoreIn.updateScore(traitPairReference, newScore)
        verify(changeTraitScoreOut).changeScore(referenceCaptor.capture(), scoreCaptor.capture())
        verifyNoMoreInteractions(changeTraitScoreOut)
        assert(traitPairReference == referenceCaptor.firstValue)
        assert(newScore == scoreCaptor.firstValue)
    }

    @ParameterizedTest
    @ValueSource(shorts = [Short.MIN_VALUE, Short.MAX_VALUE, -5, 150,-25,900])
    @DisplayName("Should throw value exception on update trait score by trait reference")
    fun shouldThrowValueExceptionOnUpdateTraitScore(newScore:Short){
        assertThrows<ApplicationException> {
            changeTraitScoreIn.updateScore(traitPairReference, newScore)
        }
    }
}