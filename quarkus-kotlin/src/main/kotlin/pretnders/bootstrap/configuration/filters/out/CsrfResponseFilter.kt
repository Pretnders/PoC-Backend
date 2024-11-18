package pretnders.bootstrap.configuration.filters.out

import pretnders.api.shared.security.CookieUtils
import pretnders.api.shared.security.CsrfTokenGeneratorIn
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerResponseContext
import jakarta.ws.rs.container.ContainerResponseFilter
import jakarta.ws.rs.ext.Provider
import org.eclipse.microprofile.config.inject.ConfigProperty

private val noCsrfGivenPaths = NoCsrfGivenPaths.getOptions()

@Provider
@ApplicationScoped
class CsrfResponseFilter (
    private var csrfTokenGeneratorIn: CsrfTokenGeneratorIn,
    private var cookieUtils: CookieUtils
): ContainerResponseFilter {

    @field:ConfigProperty(name = "rest.csrf.cookie.name")
    private lateinit var csrfCookieName: String


    override fun filter(
        requestContext: ContainerRequestContext,
        responseContext: ContainerResponseContext
    ) {
        // Ajouter le cookie à la réponse
        if (!noCsrfGivenPaths.any { noCsrfPath -> noCsrfPath.path == requestContext.uriInfo.path }) {
            val csrfToken = csrfTokenGeneratorIn.generateToken()
            val csrfCookie = cookieUtils.setUpCookie(csrfCookieName, csrfToken)
            responseContext.headers.add("Set-Cookie", csrfCookie)
            responseContext.headers.add(csrfCookieName, csrfToken)
        }
    }
}