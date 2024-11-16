package com.pretnders.shared.security

interface CsrfTokenGeneratorIn {
    fun generateToken(): String
}