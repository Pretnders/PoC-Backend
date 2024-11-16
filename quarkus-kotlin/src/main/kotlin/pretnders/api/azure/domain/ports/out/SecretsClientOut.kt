package pretnders.api.azure.domain.ports.out

interface SecretsClientOut {
    fun updateAdminCode(adminCode: String)
    fun getCurrentAdminCreationCode():String

}