package pretnders.api.account_management.domain

interface PasswordManagementIn {
    fun initPasswordRecovery(identifier: String)
    fun recoverPassword(mail: String, token: String, password: String, passwordConfirmation: String)
    fun changePassword(mail:String, currentPassword:String, password: String, passwordConfirmation: String)
}