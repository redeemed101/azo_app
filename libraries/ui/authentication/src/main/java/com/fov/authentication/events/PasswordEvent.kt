package com.fov.authentication.events

sealed class PasswordEvent {
    object DismissErrorDialog : PasswordEvent()
    object ResetPasswordClicked : PasswordEvent()
    object ResendVerificationCode : PasswordEvent()
    object ForgotPasswordClicked : PasswordEvent()
    data class VerificationCodeChanged(val code: String) : PasswordEvent()
    data class NewPasswordChanged(val password: String) : PasswordEvent()
    data class ConfirmNewPasswordChanged(val password: String) : PasswordEvent()

}