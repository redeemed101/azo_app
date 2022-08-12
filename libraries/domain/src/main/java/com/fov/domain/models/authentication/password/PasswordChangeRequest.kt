package com.fov.domain.models.authentication.password
import kotlinx.serialization.Serializable

@Serializable
data class PasswordChangeRequest(
    val username : String,
    val newPassword : String,
    val oldPassword: String
)
