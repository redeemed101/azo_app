package com.fov.authentication.states

class SettingsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val success : String? = null,
    val password : String?  = null,
    val disableAccountReason  : String?  = null,
    val deleteAccountReason: String? = null,
    val logoutDone: Boolean = false
) {


    companion object {
        fun initialise(): SettingsState = SettingsState()
    }

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(state: SettingsState) {
        var loading = state.isLoading
        var error = state.errorMessage
        var success = state.success
        var password  =  state.password
        var disableAccountReason = state.disableAccountReason
        var deleteAccountReason  = state.deleteAccountReason
        var logoutDone = state.logoutDone
        fun build(): SettingsState {
            return SettingsState(
                loading,
                error,
                success,
                password,
                disableAccountReason,
                deleteAccountReason,
                logoutDone
            )
        }

    }
}