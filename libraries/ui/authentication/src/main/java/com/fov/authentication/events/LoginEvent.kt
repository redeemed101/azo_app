package com.fov.authentication.events

sealed class LoginEvent {
    object DismissErrorDialog : LoginEvent()
    object RegisterClicked : LoginEvent()
    object LoginClicked : LoginEvent()
    object SocialLoginClicked : LoginEvent()
    object ForgotPasswordClicked : LoginEvent()
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data class SocialMediaServiceChanged(val service: String) : LoginEvent()
    data class SocialMediaTokenChanged(val token: String) : LoginEvent()
    data class SocialMediaIsFirstTimeChanged(val isFirstTime:Boolean) : LoginEvent()

}