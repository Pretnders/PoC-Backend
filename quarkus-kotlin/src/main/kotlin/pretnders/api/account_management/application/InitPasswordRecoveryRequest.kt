package pretnders.api.account_management.application

import kotlinx.serialization.Serializable

@Serializable
data class InitPasswordRecoveryRequest(val identifier:String)
