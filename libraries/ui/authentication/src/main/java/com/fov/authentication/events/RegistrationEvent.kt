package com.fov.authentication.events

import com.fov.authentication.models.UserModel
import com.fov.domain.database.models.User

sealed class RegistrationEvent {

    object DismissErrorDialog : RegistrationEvent()
    object RegistrationClicked : RegistrationEvent()
    object RegistrationNavigationClicked : RegistrationEvent()
    object VerifyCodeClicked : RegistrationEvent()
    object LoginClicked : RegistrationEvent()
    object SocialRegisterClicked : RegistrationEvent()
    object ResendVerificationCode : RegistrationEvent()
    data  class LoadUser(val user : User) : RegistrationEvent()
    data class EmailChanged(val email: String) : RegistrationEvent()
    data class ProfileUrlChanged(val url: String) : RegistrationEvent()
    data class VerificationCodeChanged(val code: String) : RegistrationEvent()
    data class UsernameChanged(val username: String) : RegistrationEvent()
    data class FullNameChanged(val fullname: String) : RegistrationEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : RegistrationEvent()
    data class PasswordChanged(val password: String) : RegistrationEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegistrationEvent()
    data class SocialMediaServiceChanged(val service: String) : RegistrationEvent()
    data class SocialMediaTokenChanged(val token: String) : RegistrationEvent()
    data class SocialMediaIsFirstTimeChanged(val isFirstTime: Boolean) : RegistrationEvent()
}


