package pretnders.api.account_management.application

import pretnders.api.account_management.domain.PasswordManagementIn
import io.quarkus.logging.Log
import jakarta.annotation.security.PermitAll
import jakarta.annotation.security.RolesAllowed
import jakarta.enterprise.context.RequestScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.jwt.Claims
import org.eclipse.microprofile.jwt.JsonWebToken
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.NO_CONTENT

@Path("/password-management")
@RequestScoped
class PasswordRecoveryResource {

    @Inject
    @field:Default
    private lateinit var passwordManagementIn: PasswordManagementIn

    @Inject
    @field:Default
    private lateinit var jwt:JsonWebToken


    @PUT
    @Path("/init-reset")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @PermitAll
    @APIResponses(
            APIResponse(responseCode = "204", description = "NO_CONTENT")
    )
    fun initiatePasswordReset(passwordRecoveryRequest: InitPasswordRecoveryRequest) {
        passwordManagementIn.initPasswordRecovery(passwordRecoveryRequest.identifier)
    }

    @PUT
    @Path("/reset-password")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @PermitAll
    fun resetPassword(passwordRecoveryRequest: PasswordRecoveryRequest) {
        passwordManagementIn.recoverPassword(passwordRecoveryRequest.mail, passwordRecoveryRequest.token,
            passwordRecoveryRequest.password,
            passwordRecoveryRequest.passwordConfirmation)
    }

    @PUT
    @Path("/change-password")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER","ADMIN")
    fun changePassword(passwordChangeRequest: PasswordChangeRequest) {
        Log.info("Starting update password")
        val mail = jwt.claim<String>(Claims.email.name).get()
        passwordManagementIn.changePassword(mail, passwordChangeRequest.currentPassword, passwordChangeRequest
            .password,
            passwordChangeRequest
            .passwordConfirmation)
    }
}