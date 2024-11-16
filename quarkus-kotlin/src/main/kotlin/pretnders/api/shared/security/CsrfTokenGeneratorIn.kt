package pretnders.api.shared.security

interface CsrfTokenGeneratorIn {
    fun generateToken(): String
}