package com.templates.domain.services

import io.smallrye.jwt.build.Jwt
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.jwt.Claims
import java.time.Instant
import java.time.temporal.ChronoUnit

@ApplicationScoped
class JwtTokenGenerator {
    @field:ConfigProperty(name = "mp.jwt.verify.issuer")
    private lateinit var issuer: String

    @field:ConfigProperty(name = "claims.origin")
    private lateinit var origin: String

    fun getToken(mail: String?, userType: String): String {
        return Jwt.issuer(issuer)
            .upn(mail)
            .groups(HashSet(listOf(userType)))
            .expiresAt(Instant.now().plus(14, ChronoUnit.DAYS))
            .claim(Claims.address.name, origin)
            .sign()
    }
}