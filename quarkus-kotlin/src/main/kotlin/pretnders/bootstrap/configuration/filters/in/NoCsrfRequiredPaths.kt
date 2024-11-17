package pretnders.bootstrap.configuration.filters.`in`

enum class NoCsrfRequiredPaths(val path:String) {
    PRETNDER_CONNEXION_PATH("/login/pretnder"),
    ADMIN_CONNEXION_PATH("/login/admin"),
    CREATE_PRETNDER_PATH("/create-pretnder"),
    HEALTH_CHECK_PATH("/healthcheck"),
    HEALTH_CHECK_SECURED_PATH("/healthcheck/secured"),
    PASSWORD_RECOVERY_INIT_RESET_PATH("/password-management/init-reset",),
    PASSWORD_RECOVERY_RESET_PASSWORD_PATH("/password-management/reset-password",),
    ADMIN_CREATION_PATH("/admin-creation");
    companion object {
        fun getOptions():List<NoCsrfPath>{
            return entries.map {
                NoCsrfPath(it.path)
            }
        }
    }

    data class NoCsrfPath(val path: String)
}