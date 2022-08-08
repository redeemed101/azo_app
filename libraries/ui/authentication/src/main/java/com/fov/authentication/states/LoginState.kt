package com.fov.authentication.states

class LoginState(
    val emailAddress: String = "",
    val password: String = "",
    val fullname : String = "",
    var username :  String = "",
    val isLoginContentValid: Boolean = false,
    val isRestore : Boolean = false,
    val isLoading: Boolean = false,
    val success: Boolean? = null,
    val errorMessage: String? = null,
    val socialMediaService : String = "",
    val socialMediaToken : String = "",
    val socialMediaFirstTime : Boolean = false,
    val loginDone : Boolean = false

) {
    companion object {
        fun initialise(): LoginState = LoginState()
    }

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(state: LoginState) {
        var userEmail = state.emailAddress
        var userPassword = state.password
        var fullname = state.fullname
        var username = state.username
        var isRestore = state.isRestore
        var isLoginContentValid = userEmail.trim().isNotEmpty() &&
                userPassword.trim().isNotEmpty()
        var loading = state.isLoading
        var success = state.success
        var error = state.errorMessage
        var socialMediaService = state.socialMediaService
        var socialMediaToken = state.socialMediaToken
        var socialMediaFirstTime = state.socialMediaFirstTime
        var loginDone = state.loginDone

        fun build(): LoginState {
            return LoginState(
                userEmail,
                userPassword,
                fullname,
                username,
                isLoginContentValid,
                isRestore,
                loading,
                success,
                error,
                socialMediaService,
                socialMediaToken,
                socialMediaFirstTime,
                loginDone
            )
        }
    }
}