package pretnders.api.pretnders.application.dto.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import java.io.Serializable

/**
 * DTO for {@link pretnders.app.pretnders.persistence.entities.PretnderDetailsEntity}
 */
@kotlinx.serialization.Serializable
data class ChangeBiographyRequest(
    @field:Size(min = 32, max = 32) @field:NotEmpty @field:NotBlank val reference: String,
    @field:Size(
        message = "Biography too short",
        min = 150,
        max = 5000
    ) @field:NotEmpty @field:NotBlank val biography: String
) : Serializable