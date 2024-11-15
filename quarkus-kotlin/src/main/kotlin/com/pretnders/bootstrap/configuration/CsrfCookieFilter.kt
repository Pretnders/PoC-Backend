package com.pretnders.bootstrap.configuration

import jakarta.annotation.Priority
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerRequestFilter
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.Provider
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.jwt.JsonWebToken

private const val ADMIN_CODE_PATH = "/admin-code"


private const val CONNEXION_PATH = "/login"

private const val CREATE_PRETENDER_PATH = "/create-pretnder"

private const val HEALTHCHECK_PATH = "/healthcheck"


private const val PASSWORD_RECOVERY_INIT_RESET_PATH = "/password-management/init-reset"

private const val PASSWORD_RECOVERY_RESET_PATH = "/password-management/reset-password"

private const val ADMIN_CREATION_PATH = "/admin-creation"
private const val PP_PATH = "/profile-pictures"


@Provider
@Priority(1)
class CsrfCookieFilter:ContainerRequestFilter {

    @ConfigProperty(name = "quarkus.rest.csrf.cookie.name")
    private lateinit var csrfTokenName:String

    @Inject
    @field:Default
    private lateinit var jwt: JsonWebToken

    override fun filter(requestContext: ContainerRequestContext) {
        val csrfCookie = requestContext.cookies[csrfTokenName]
        val csrfHeader = requestContext.getHeaderString(csrfTokenName)
        if(requestContext.uriInfo.path.startsWith
                (PP_PATH)||requestContext.uriInfo.path.startsWith
                (ADMIN_CODE_PATH) || requestContext.uriInfo.path.startsWith
                (CONNEXION_PATH) || requestContext.uriInfo.path.startsWith
                (CREATE_PRETENDER_PATH)|| requestContext.uriInfo.path.startsWith
                (HEALTHCHECK_PATH)||  requestContext.uriInfo.path == PASSWORD_RECOVERY_INIT_RESET_PATH
            || requestContext.uriInfo.path ==
            PASSWORD_RECOVERY_RESET_PATH
            || requestContext.uriInfo.path ==
            ADMIN_CREATION_PATH|| requestContext.uriInfo.path.startsWith("/pretnder-profile/nickname")){
            return
        }

        if (csrfCookie == null || csrfCookie.value.isEmpty() || csrfCookie.value != csrfHeader) {
            requestContext.abortWith(
                Response.status(Response.Status.EXPECTATION_FAILED)
                .entity("CSRF token error").build())
        }
    }
}