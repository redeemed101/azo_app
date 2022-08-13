package com.fov.authentication.states

import com.fov.domain.database.models.User

class PasswordState(
    val verificationCode: String = String(),
    val email : String = String(),
    val newPassword: String = String(),
    val confirmNewPassword : String = String(),
    val isResetPasswordContentValid: Boolean = false,
    val isLoading: Boolean = false,
    val success: Boolean? = null,
    val errorMessage: String? = null,
    val isRestore : Boolean = false,
    val successMessage : String = String(),
    val user: User? = null

) {
    companion object {
        fun initialise(): PasswordState = PasswordState()
    }

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(state: PasswordState) {
        var verificationCode = state.verificationCode
        var email = state.email
        var newPassword = state.newPassword
        var confirmNewPassword = state.confirmNewPassword
        var isResetPasswordContentValid = verificationCode.trim().isNotEmpty() &&
                confirmNewPassword.trim().isNotEmpty() && newPassword.trim().isNotEmpty() &&
                confirmNewPassword.trim() == newPassword.trim()
        var loading = state.isLoading
        var success = state.success
        var error = state.errorMessage
        var isRestore = state.isRestore
        var successMessage = state.successMessage
        var user = state.user

        fun build(): PasswordState {
            return PasswordState(
               verificationCode,
                email,
                newPassword ,
                confirmNewPassword,
                isResetPasswordContentValid,
                loading,
                success,
                error,
                isRestore,
                successMessage,
                user
            )
        }
    }
}