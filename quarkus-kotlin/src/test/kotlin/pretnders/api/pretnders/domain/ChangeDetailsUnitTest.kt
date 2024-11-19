package pretnders.api.pretnders.domain

import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import pretnders.api.pretnders.domain.models.details_enums.*
import pretnders.api.pretnders.domain.ports.`in`.ChangeDetailsIn
import pretnders.api.pretnders.domain.ports.out.ChangeDetailsOut
import pretnders.api.shared.errors.ApplicationException

@QuarkusTest
@DisplayName("Should change pretnder details domain unit tests")
class ChangeDetailsUnitTest {

    @Inject
    private lateinit var changeDetailsIn: ChangeDetailsIn

    @InjectMock
    private lateinit var changeDetailsOut: ChangeDetailsOut

    private val reference: String = "123456789"

    @ParameterizedTest
    @ValueSource(strings = ["185", "158", "167", "198", "150", "172", "180", "199", "174"])
    @DisplayName("Should change height")
    fun shouldChangeHeight(newHeight: String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(changeDetailsOut).changeHeight(reference, newHeight)
        changeDetailsIn.changeHeight(reference, newHeight)
        verify(changeDetailsOut).changeHeight(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newHeight)
        verifyNoMoreInteractions(changeDetailsOut)
    }

    @ParameterizedTest
    @ValueSource(strings = ["310", "2", "12", "8000", "4000", "999"])
    @DisplayName("Should throw format exception on change height")
    fun shouldThrowFormatExceptionOnChangeHeight(newHeight: String) {
        assertThrows<ApplicationException> {
            changeDetailsIn.changeHeight(reference, newHeight)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["110", "119", "260", "280"])
    @DisplayName("Should throw value exception on change height")
    fun shouldThrowValueExceptionOnChangeHeight(newHeight: String) {
        assertThrows<ApplicationException> {
            changeDetailsIn.changeHeight(reference, newHeight)
        }
    }

    @Test
    @DisplayName("Should change body type")
    fun shouldChangeBodyType() {
        val values = BodyTypes.entries.toTypedArray()
        for (value in values) {
            doNothing().`when`(changeDetailsOut).changeBodyType(reference, value.name)
            changeDetailsIn.changeBodyType(reference, value)
            verify(changeDetailsOut).changeBodyType(reference,value.name)
        }
        verifyNoMoreInteractions(changeDetailsOut)
    }


    @Test
    @DisplayName("Should change diet")
    fun shouldChangeDiet() {
        val values = Diets.entries.toTypedArray()
        for (value in values) {
            doNothing().`when`(changeDetailsOut).changeDiet(reference, value.name)
            changeDetailsIn.changeDiet(reference, value)
            verify(changeDetailsOut).changeDiet(reference,value.name)
        }
        verifyNoMoreInteractions(changeDetailsOut)
    }


    @Test
    @DisplayName("Should change belief")
    fun shouldChangeBelief() {
        val values = Beliefs.entries.toTypedArray()
        for (value in values) {
            doNothing().`when`(changeDetailsOut).changeBeliefs(reference, value.name)
            changeDetailsIn.changeBeliefs(reference, value)
            verify(changeDetailsOut).changeBeliefs(reference,value.name)
        }
        verifyNoMoreInteractions(changeDetailsOut)
    }


    @Test
    @DisplayName("Should change smokes")
    fun shouldChangeSmokes() {
        val values = Occurrences.entries.toTypedArray()

        for (value in values) {
            doNothing().`when`(changeDetailsOut).changeSmokes(reference, value.name)
            changeDetailsIn.changeSmokes(reference, value)
            verify(changeDetailsOut).changeSmokes(reference,value.name)
        }
        verifyNoMoreInteractions(changeDetailsOut)
    }

    @Test
    @DisplayName("Should change drinks")
    fun shouldChangeDrinks() {
        val values = Occurrences.entries.toTypedArray()

        for (value in values) {
            doNothing().`when`(changeDetailsOut).changeDrinks(reference, value.name)
            changeDetailsIn.changeDrinks(reference, value)
            verify(changeDetailsOut).changeDrinks(reference,value.name)
        }
        verifyNoMoreInteractions(changeDetailsOut)
    }

    @Test
    @DisplayName("Should change social status")
    fun shouldChangeSocialStatus() {
        val values = SocialStatus.entries.toTypedArray()
        for (value in values) {
            doNothing().`when`(changeDetailsOut).changeSocialStatus(reference, value.name)
            changeDetailsIn.changeSocialStatus(reference, value)
            verify(changeDetailsOut).changeSocialStatus(reference,value.name)
        }
        verifyNoMoreInteractions(changeDetailsOut)
    }

    @ParameterizedTest
    @ValueSource(
        strings = ["Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?"]
    )
    @DisplayName("Should change biography")
    fun shouldChangeBiography(newBiography: String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(changeDetailsOut).changeBiography(reference, newBiography)
        changeDetailsIn.changeBiography(reference, newBiography)
        verify(changeDetailsOut).changeBiography(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newBiography)
        verifyNoMoreInteractions(changeDetailsOut)
    }

    @ParameterizedTest
    @ValueSource(strings = ["Paris", "Lille", "Lens", "Lyon", "Loos-En-Gohelle"])
    @DisplayName("Should change city")
    fun shouldChangeCity(newCity: String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(changeDetailsOut).changeCity(reference, newCity)
        changeDetailsIn.changeCity(reference, newCity)
        verify(changeDetailsOut).changeCity(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newCity)
        verifyNoMoreInteractions(changeDetailsOut)
    }

    @ParameterizedTest
    @ValueSource(strings = ["France"])
    @DisplayName("Should change country")
    fun shouldChangeCountry(newCountry: String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(changeDetailsOut).changeCountry(reference, newCountry)
        changeDetailsIn.changeCountry(reference, newCountry)
        verify(changeDetailsOut).changeCountry(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newCountry)
        verifyNoMoreInteractions(changeDetailsOut)
    }

    @Test
    @DisplayName("Should change orientation")
    fun shouldChangeOrientation() {
        val values = SexualOrientations.entries.toTypedArray()
        for (value in values) {
            doNothing().`when`(changeDetailsOut).changeOrientation(reference, value.name)
            changeDetailsIn.changeOrientation(reference, value)
            verify(changeDetailsOut).changeOrientation(reference,value.name)
        }
        verifyNoMoreInteractions(changeDetailsOut)
    }

    @Test
    @DisplayName("Should change orientation")
    fun shouldChangeGender() {
        val values = Genders.entries.toTypedArray()
        for (value in values) {
            doNothing().`when`(changeDetailsOut).changeGender(reference, value.name)
            changeDetailsIn.changeGender(reference, value)
            verify(changeDetailsOut).changeGender(reference,value.name)
        }
        verifyNoMoreInteractions(changeDetailsOut)
    }

    @ParameterizedTest
    @ValueSource(strings = ["Athos", "Mordred", "BGDU69", "Loveuse du 92"])
    @DisplayName("Should change nickname")
    fun shouldChangeNickname(newNickname: String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(changeDetailsOut).changeNickname(reference, newNickname)
        changeDetailsIn.changeNickname(reference, newNickname)
        verify(changeDetailsOut).changeNickname(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newNickname)
        verifyNoMoreInteractions(changeDetailsOut)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "lo"])
    @DisplayName("Should change nickname")
    fun shouldThrowExceptionOnChangeNickname(newNickname: String) {
        assertThrows<ApplicationException> {
            changeDetailsIn.changeNickname(reference, newNickname)
        }
    }
}