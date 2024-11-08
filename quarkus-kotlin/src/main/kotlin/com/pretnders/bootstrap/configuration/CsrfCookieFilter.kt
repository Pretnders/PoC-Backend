package com.pretnders.bootstrap.configuration

import com.pretnders.domain.services.CsrfTokenCache
import jakarta.annotation.Priority
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerRequestFilter
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.Provider
import org.eclipse.microprofile.jwt.JsonWebToken

private const val ADMIN_CODE_PATH = "/admin-code"

private const val CSRF_TOKEN_PATH = "/csrf-token"

private const val CONNEXION_PATH = "/connection"

private const val CREATE_PRETENDER_PATH = "/create-pretender"

private const val HEALTHCHECK_PATH = "/healthcheck"


private const val PASSWORD_RECOVERY_INIT_RESET_PATH = "/password-recovery/init-reset"

private const val PASSWORD_RECOVERY_RESET_PATH = "/password-recovery/reset-password"

private const val WEBSOCKETS_PATH = "/password-recovery/reset-password"


@Provider
@Priority(1)
class CsrfCookieFilter:ContainerRequestFilter {

    @Inject
    @field:Default
    private lateinit var csrfTokenCache: CsrfTokenCache

    @Inject
    @field:Default
    private lateinit var jwt: JsonWebToken

    override fun filter(requestContext: ContainerRequestContext) {
        val csrfCookie = requestContext.cookies["csrf-token"]
        val mail = jwt.name
        if(requestContext.uriInfo.path.startsWith
                (ADMIN_CODE_PATH)|| requestContext.uriInfo.path.startsWith
                (CSRF_TOKEN_PATH) || requestContext.uriInfo.path.startsWith
                (CONNEXION_PATH) || requestContext.uriInfo.path.startsWith
                (CREATE_PRETENDER_PATH)|| requestContext.uriInfo.path.startsWith
                (HEALTHCHECK_PATH)||  requestContext.uriInfo.path == PASSWORD_RECOVERY_INIT_RESET_PATH
            || requestContext.uriInfo.path ==
            PASSWORD_RECOVERY_RESET_PATH){
            return
        }
        if (csrfCookie == null || csrfCookie.value.isEmpty() || csrfCookie.value != csrfTokenCache
            .getUserToken(mail)) {
            requestContext.abortWith(
                Response.status(Response.Status.EXPECTATION_FAILED)
                .entity("CSRF token error").build())
        }
    }
}