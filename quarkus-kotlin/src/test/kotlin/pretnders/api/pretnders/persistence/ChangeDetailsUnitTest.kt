package pretnders.api.pretnders.persistence

import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import pretnders.api.pretnders.domain.ports.out.ChangeDetailsOut
import pretnders.api.pretnders.persistence.repositories.PretnderDetailsCommandsRepository
import pretnders.api.pretnders.persistence.repositories.PretndersCommandsRepository

@QuarkusTest
@DisplayName("Should change pretnder details unit tests")
class ChangeDetailsUnitTest {

    @Inject
    private lateinit var changeDetailsOut: ChangeDetailsOut

    @InjectMock
    private lateinit var pretndersCommandsRepository: PretndersCommandsRepository

    @InjectMock
    private lateinit var pretndersDetailsCommandsRepository: PretnderDetailsCommandsRepository

    private val reference:String = "123456789"

    @ParameterizedTest
    @ValueSource(strings = ["185","158","167","198","150","172","180","199","174",])
    @DisplayName("Should change height")
    @Order(1)
    fun shouldChangeHeight(newHeight:String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(pretndersDetailsCommandsRepository).changeHeight(reference, newHeight)
        changeDetailsOut.changeHeight(reference, newHeight)
        verify(pretndersDetailsCommandsRepository).changeHeight(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newHeight)
        verifyNoMoreInteractions(pretndersDetailsCommandsRepository)
    }

    @ParameterizedTest
    @ValueSource(strings = ["THIN","THICK","ATHLETIC","CURVY",])
    @DisplayName("Should change body type")
    @Order(1)
    fun shouldChangeBodyType(newBodyType:String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(pretndersDetailsCommandsRepository).changeBodyType(reference, newBodyType)
        changeDetailsOut.changeBodyType(reference, newBodyType)
        verify(pretndersDetailsCommandsRepository).changeBodyType(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newBodyType)
        verifyNoMoreInteractions(pretndersDetailsCommandsRepository)
    }


    @ParameterizedTest
    @ValueSource(strings = ["PISCIVORE","OMNIVORE","VEGETARIAN","VEGAN","HALAL",])
    @DisplayName("Should change diet")
    @Order(1)
    fun shouldChangeDiet(newDiet:String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(pretndersDetailsCommandsRepository).changeDiet(reference, newDiet)
        changeDetailsOut.changeDiet(reference, newDiet)
        verify(pretndersDetailsCommandsRepository).changeDiet(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newDiet)
        verifyNoMoreInteractions(pretndersDetailsCommandsRepository)
    }



    @ParameterizedTest
    @ValueSource(strings = ["ATHEIST","MUSLIM","CHRISTIAN","JEW",])
    @DisplayName("Should change belief")
    @Order(1)
    fun shouldChangeBelief(newBeliefs:String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(pretndersDetailsCommandsRepository).changeBeliefs(reference, newBeliefs)
        changeDetailsOut.changeBeliefs(reference, newBeliefs)
        verify(pretndersDetailsCommandsRepository).changeBeliefs(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newBeliefs)
        verifyNoMoreInteractions(pretndersDetailsCommandsRepository)
    }


    @ParameterizedTest
    @ValueSource(strings = ["OFTEN","NEVER","RARELY","SOCIALLY",])
    @DisplayName("Should change smokes")
    @Order(1)
    fun shouldChangeSmokes(newOccurrence:String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(pretndersDetailsCommandsRepository).changeSmokes(reference, newOccurrence)
        changeDetailsOut.changeSmokes(reference, newOccurrence)
        verify(pretndersDetailsCommandsRepository).changeSmokes(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newOccurrence)
        verifyNoMoreInteractions(pretndersDetailsCommandsRepository)
    }


    @ParameterizedTest
    @ValueSource(strings = ["OFTEN","NEVER","RARELY","SOCIALLY",])
    @DisplayName("Should change drinks")
    @Order(1)
    fun shouldChangeDrinks(newOccurrence:String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(pretndersDetailsCommandsRepository).changeDrinks(reference, newOccurrence)
        changeDetailsOut.changeDrinks(reference, newOccurrence)
        verify(pretndersDetailsCommandsRepository).changeDrinks(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newOccurrence)
        verifyNoMoreInteractions(pretndersDetailsCommandsRepository)
    }

    @ParameterizedTest
    @ValueSource(strings = ["STUDENT","EMPLOYED","INACTIVE"])
    @DisplayName("Should change social status")
    @Order(1)
    fun shouldChangeSocialStatus(newSocialStatus:String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(pretndersDetailsCommandsRepository).changeSocialStatus(reference, newSocialStatus)
        changeDetailsOut.changeSocialStatus(reference, newSocialStatus)
        verify(pretndersDetailsCommandsRepository).changeSocialStatus(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newSocialStatus)
        verifyNoMoreInteractions(pretndersDetailsCommandsRepository)
    }

    @ParameterizedTest
    @ValueSource(strings = ["Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.","Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?"])
    @DisplayName("Should change biography")
    @Order(1)
    fun shouldChangeBiography(newBiography:String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(pretndersDetailsCommandsRepository).changeBiography(reference, newBiography)
        changeDetailsOut.changeBiography(reference, newBiography)
        verify(pretndersDetailsCommandsRepository).changeBiography(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newBiography)
        verifyNoMoreInteractions(pretndersDetailsCommandsRepository)
    }

    @ParameterizedTest
    @ValueSource(strings = ["Paris", "Lille", "Lens", "Lyon", "Loos-En-Gohelle"])
    @DisplayName("Should change city")
    @Order(1)
    fun shouldChangeCity(newCity:String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(pretndersDetailsCommandsRepository).changeCity(reference, newCity)
        changeDetailsOut.changeCity(reference, newCity)
        verify(pretndersDetailsCommandsRepository).changeCity(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newCity)
        verifyNoMoreInteractions(pretndersDetailsCommandsRepository)
    }

    @ParameterizedTest
    @ValueSource(strings = ["France"])
    @DisplayName("Should change country")
    @Order(1)
    fun shouldChangeCountry(newCountry:String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(pretndersDetailsCommandsRepository).changeCountry(reference, newCountry)
        changeDetailsOut.changeCountry(reference, newCountry)
        verify(pretndersDetailsCommandsRepository).changeCountry(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newCountry)
        verifyNoMoreInteractions(pretndersDetailsCommandsRepository)
    }


    @ParameterizedTest
    @ValueSource(strings = ["HETEROSEXUAL","HOMOSEXUAL","ASEXUAL","BISEXUAL","HETEROFLEXIBLE"])
    @DisplayName("Should change orientation")
    @Order(1)
    fun shouldChangeOrientation(newOrientation:String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(pretndersDetailsCommandsRepository).changeOrientation(reference, newOrientation)
        changeDetailsOut.changeOrientation(reference, newOrientation)
        verify(pretndersDetailsCommandsRepository).changeOrientation(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newOrientation)
        verifyNoMoreInteractions(pretndersDetailsCommandsRepository)
    }

    @ParameterizedTest
    @ValueSource(strings = ["CISMALE","CISFEMALE","NONBINARY"])
    @DisplayName("Should change gender")
    @Order(1)
    fun shouldChangeGender(newGender:String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(pretndersDetailsCommandsRepository).changeGender(reference, newGender)
        changeDetailsOut.changeGender(reference, newGender)
        verify(pretndersDetailsCommandsRepository).changeGender(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newGender)
        verifyNoMoreInteractions(pretndersDetailsCommandsRepository)
    }

    @ParameterizedTest
    @ValueSource(strings = ["Athos","Mordred","BGDU69","Loveuse du 92"])
    @DisplayName("Should change nickname")
    @Order(1)
    fun shouldChangeNickname(newNickname:String) {
        val captor = argumentCaptor<String>()
        doNothing().`when`(pretndersCommandsRepository).changeNickname(reference, newNickname)
        changeDetailsOut.changeNickname(reference, newNickname)
        verify(pretndersCommandsRepository).changeNickname(captor.capture(), captor.capture())
        assert(captor.firstValue == reference)
        assert(captor.secondValue == newNickname)
        verifyNoMoreInteractions(pretndersDetailsCommandsRepository)
    }
}