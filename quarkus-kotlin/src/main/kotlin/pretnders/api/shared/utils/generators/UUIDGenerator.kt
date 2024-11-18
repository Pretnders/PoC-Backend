package pretnders.api.shared.utils.generators
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import java.util.*

@ApplicationScoped
class UUIDGenerator {

    private fun generateUUID(): UUID {
        Log.info("Generating UUID reference.")
        val newRandomBusinessReference = UUID.randomUUID()
        Log.info("UUID base Reference $newRandomBusinessReference Successfully generated.")
        return newRandomBusinessReference
    }
    fun getNewUUID(): String {
        return UUIDFormatter.formatUUIDSequence(generateUUID(), "")
    }
}