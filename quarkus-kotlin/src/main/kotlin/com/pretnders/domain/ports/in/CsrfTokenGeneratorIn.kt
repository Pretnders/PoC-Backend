package com.pretnders.domain.ports.`in`

interface CsrfTokenGeneratorIn {
    fun generateToken(identifier:String): String
}