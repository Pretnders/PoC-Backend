package pretnders.api.pretnders.application.controllers

import pretnders.api.account_management.application.OtpRequest
import pretnders.api.account_management.domain.VerifyAccountsIn
import jakarta.annotation.security.RolesAllowed
import jakarta.enterprise.context.RequestScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.jwt.JsonWebToken
import io.quarkus.logging.Log
import org.eclipse.microprofile.jwt.Claims
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse.StatusCode.NO_CONTENT

@Path("/verify-account")
@RequestScoped
class ManageAccountVerificationEndpoint (
    private val verifyAccountsIn: VerifyAccountsIn,
    private val jsonWebToken: JsonWebToken
) {


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    fun verifyClientAccount(otpRequest: OtpRequest) {
        Log.info("Verifying user account")
        val mail = jsonWebToken.claim<String>(Claims.email.name).get()
        verifyAccountsIn.verifyPretnderAccount(mail, otpRequest.otpCode)
    }

    @PUT
    @Path("/new-otp")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(NO_CONTENT)
    @RolesAllowed("PRETNDER")
    fun generateNewOtpCode() {
        Log.info("Initiating new OTP")
        val mail = jsonWebToken.claim<String>(Claims.email.name).get()
        verifyAccountsIn.generateNewOtpCode(mail)
    }
}