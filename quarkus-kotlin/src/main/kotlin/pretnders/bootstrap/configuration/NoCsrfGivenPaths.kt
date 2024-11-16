package pretnders.bootstrap.configuration


enum class NoCsrfGivenPaths(val path:String) {
    HEALTH_CHECK_PATH("/healthcheck"),
    HEALTH_CHECK_SECURED_PATH("/healthcheck/secured"),
    PASSWORD_RECOVERY_INIT_RESET_PATH("/password-management/init-reset",),
    PASSWORD_RECOVERY_RESET_PASSWORD_PATH("/password-management/reset-password",);
    companion object {
        fun getOptions():List<NoCsrfPath>{
            return entries.map {
                NoCsrfPath(it.path)
            }
        }
    }

    data class NoCsrfPath(val path: String)
}