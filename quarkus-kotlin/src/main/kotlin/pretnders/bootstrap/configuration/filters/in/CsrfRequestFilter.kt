package pretnders.bootstrap.configuration.filters.`in`

import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerRequestFilter
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.Provider
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.jwt.JsonWebToken



private val noCsrfRequiredPaths = NoCsrfRequiredPaths.getOptions()

@Provider
@ApplicationScoped
class CsrfRequestFilter:ContainerRequestFilter {

    @ConfigProperty(name = "rest.csrf.cookie.name")
    private lateinit var csrfTokenName:String

    @Inject
    @field:Default
    private lateinit var jwt: JsonWebToken

    override fun filter(requestContext: ContainerRequestContext) {
        if(noCsrfRequiredPaths.any { noCsrfPath -> requestContext.uriInfo.path.startsWith(noCsrfPath.path)}){
            return
        }
        val csrfCookie = requestContext.cookies[csrfTokenName]
        val csrfHeader = requestContext.getHeaderString(csrfTokenName)
        if (csrfCookie == null || csrfCookie.value.isEmpty() || csrfCookie.value != csrfHeader) {
            requestContext.abortWith(
                Response.status(Response.Status.EXPECTATION_FAILED)
                .entity("CSRF token error").build())
        }
    }
}