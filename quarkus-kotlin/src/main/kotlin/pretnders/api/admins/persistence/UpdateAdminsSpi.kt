package pretnders.api.admins.persistence

import pretnders.api.admins.domain.UpdateAdminsOut
import pretnders.api.shared.utils.handlers.PersistenceExceptionsHandler.handlePersistenceExceptions
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.hibernate.exception.ConstraintViolationException

@ApplicationScoped
@Transactional
class UpdateAdminsSpi: UpdateAdminsOut {

    @Inject
    @field:Default
    private lateinit var adminsCommandsRepository: AdminsCommandsRepository

    override fun updateNickname(mail: String, newNickname: String) {
        try {
            adminsCommandsRepository.updateNickname(mail, newNickname)
            Log.debug(String.format("Admin %s nickname was updated", mail))
        } catch (e: ConstraintViolationException) {
            handlePersistenceExceptions(e)
        }

    }
    override fun updateProfilePicture(phoneNumber: String, newProfilePicture:String) {
        try {
            adminsCommandsRepository.updateProfilePicture(phoneNumber, newProfilePicture)
            Log.debug(String.format("User %s profile picture was updated", phoneNumber))
        } catch (e: ConstraintViolationException) {
            handlePersistenceExceptions(e)
        }
    }
}