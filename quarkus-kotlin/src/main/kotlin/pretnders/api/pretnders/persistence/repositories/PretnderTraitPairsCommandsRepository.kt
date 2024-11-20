package pretnders.api.pretnders.persistence.repositories

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.logging.Log
import pretnders.api.pretnders.persistence.entities.PretnderTraitPairsEntity
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.hibernate.exception.ConstraintViolationException
import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum
import pretnders.api.shared.utils.handlers.PersistenceExceptionsHandler.handlePersistenceExceptions
import java.sql.SQLException


@ApplicationScoped
class PretnderTraitPairsCommandsRepository : PanacheRepository<PretnderTraitPairsEntity> {

    @Transactional
    fun updateScore(traitPairReference: String, score: Short) {
        try {
            update("score = :score WHERE reference = :reference", mapOf(
                "reference" to traitPairReference,
                "score" to score
            ))
        } catch (sqlException: ConstraintViolationException) {
            handlePersistenceExceptions(sqlException)
        }
    }
}