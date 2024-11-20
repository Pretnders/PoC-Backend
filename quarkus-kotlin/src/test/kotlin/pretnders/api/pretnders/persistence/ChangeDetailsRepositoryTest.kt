package pretnders.api.pretnders.persistence

import io.quarkus.test.TestTransaction
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import pretnders.api.pretnders.domain.models.details_enums.*
import pretnders.api.pretnders.persistence.repositories.PretnderDetailsCommandsRepository

@QuarkusTest
class ChangeDetailsRepositoryTest {
    @Inject
    private lateinit var pretndersDetailsCommandsRepository: PretnderDetailsCommandsRepository

    @Test
    @TestTransaction
    fun shouldChangeHeight() {
        val ref = "1f0a4e2005ee46ea8fca3a103f33cd8b"
        val height = "198"
        assertDoesNotThrow {
            pretndersDetailsCommandsRepository.changeHeight(ref, height)
            val inDb = pretndersDetailsCommandsRepository.find("reference", ref).firstResult()
            assert(height == inDb!!.height)
        }
    }

    @Test
    @TestTransaction
    fun shouldChangeBodyType() {
        val ref = "1f0a4e2005ee46ea8fca3a103f33cd8b"
        val bodyTypes = BodyTypes.entries.toTypedArray()
        for (bodyType in bodyTypes) {
            val value = bodyType.name
            assertDoesNotThrow {
                pretndersDetailsCommandsRepository.changeBodyType(ref, value)
            }
            val inDb = pretndersDetailsCommandsRepository.find("reference", ref).firstResult()!!
            assert(value == inDb.bodyType)
            pretndersDetailsCommandsRepository.getEntityManager().clear()
        }
    }


    @Test
    @TestTransaction
    fun shouldChangeBelief() {
        val ref = "1f0a4e2005ee46ea8fca3a103f33cd8b"
        val beliefs = Beliefs.entries.toTypedArray()
        for (belief in beliefs) {
            assertDoesNotThrow {
                val value = belief.name
                pretndersDetailsCommandsRepository.changeBeliefs(ref, value)
                val inDb = pretndersDetailsCommandsRepository.find("reference", ref).firstResult()
                assert(value == inDb!!.beliefs)
                pretndersDetailsCommandsRepository.getEntityManager().clear()
            }
        }
    }

    @Test
    @TestTransaction
    fun shouldChangeGender() {
        val ref = "1f0a4e2005ee46ea8fca3a103f33cd8b"
        val genders = Genders.entries.toTypedArray()
        for (gender in genders) {
            assertDoesNotThrow {
                val value = gender.name
                pretndersDetailsCommandsRepository.changeGender(ref, value)
                val inDb = pretndersDetailsCommandsRepository.find("reference", ref).firstResult()!!
                assert(value == inDb.gender)
                pretndersDetailsCommandsRepository.getEntityManager().clear()
            }
        }
    }

    @Test
    @TestTransaction
    fun shouldChangeOrientation() {
        val ref = "1f0a4e2005ee46ea8fca3a103f33cd8b"
        val orientations = SexualOrientations.entries.toTypedArray()
        for (orientation in orientations) {
            assertDoesNotThrow {
                val value = orientation.name
                pretndersDetailsCommandsRepository.changeOrientation(ref, value)
                val inDb = pretndersDetailsCommandsRepository.find("reference", ref).firstResult()!!
                assert(value == inDb.orientation)
                pretndersDetailsCommandsRepository.getEntityManager().clear()
            }
        }
    }

    @Test
    @TestTransaction
    fun shouldChangeSocialStatus() {
        val ref = "1f0a4e2005ee46ea8fca3a103f33cd8b"
        val socialStatuses = SocialStatus.entries.toTypedArray()
        for (socialStatus in socialStatuses) {
            assertDoesNotThrow {
                val value = socialStatus.name
                pretndersDetailsCommandsRepository.changeSocialStatus(ref, value)
                val inDb = pretndersDetailsCommandsRepository.find("reference", ref).firstResult()!!
                assert(value == inDb.socialStatus)
                pretndersDetailsCommandsRepository.getEntityManager().clear()
            }
        }
    }

    @Test
    @TestTransaction
    fun shouldChangeSmokesOccurrence() {
        val ref = "1f0a4e2005ee46ea8fca3a103f33cd8b"
        val occurrences = Occurrences.entries.toTypedArray()
        for (occurrence in occurrences) {
            assertDoesNotThrow {
                val value = occurrence.name
                pretndersDetailsCommandsRepository.changeSmokes(ref, value)
                val inDb = pretndersDetailsCommandsRepository.find("reference", ref).firstResult()!!
                assert(value == inDb.smokes)
                pretndersDetailsCommandsRepository.getEntityManager().clear()
            }
        }
    }

    @Test
    @TestTransaction
    fun shouldChangeDrinksOccurrence() {
        val ref = "1f0a4e2005ee46ea8fca3a103f33cd8b"
        val occurrences = Occurrences.entries.toTypedArray()
        for (occurrence in occurrences) {
            assertDoesNotThrow {
                val value = occurrence.name
                pretndersDetailsCommandsRepository.changeDrinks(ref, value)
                val inDb = pretndersDetailsCommandsRepository.find("reference", ref).firstResult()!!
                assert(value == inDb.drinks)
                pretndersDetailsCommandsRepository.getEntityManager().clear()
            }
        }
    }


    @Test
    @TestTransaction
    fun shouldChangeDiet() {
        val ref = "1f0a4e2005ee46ea8fca3a103f33cd8b"
        val diets = Diets.entries.toTypedArray()
        for (diet in diets) {
            assertDoesNotThrow {
                val value = diet.name
                pretndersDetailsCommandsRepository.changeDiet(ref, value)
                val inDb = pretndersDetailsCommandsRepository.find("reference", ref).firstResult()!!
                assert(value == inDb.diet)
                pretndersDetailsCommandsRepository.getEntityManager().clear()
            }
        }
    }
}