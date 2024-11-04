package com.templates.domain.ports.`in`

interface CsrfTokenGeneratorIn {
    fun generateToken(identifier:String): String
}