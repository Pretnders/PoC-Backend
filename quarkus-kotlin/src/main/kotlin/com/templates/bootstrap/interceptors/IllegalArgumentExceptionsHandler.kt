package com.templates.bootstrap.interceptors

import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.jboss.resteasy.reactive.RestResponse.StatusCode

@Provider
class IllegalArgumentExceptionsHandler : ExceptionMapper<IllegalArgumentException> {
    @Produces(MediaType.TEXT_PLAIN)
    override fun toResponse(e: IllegalArgumentException): Response {
        return Response.status(Response.Status.fromStatusCode(StatusCode.NOT_ACCEPTABLE))
            .entity(e.message).build()
    }
}