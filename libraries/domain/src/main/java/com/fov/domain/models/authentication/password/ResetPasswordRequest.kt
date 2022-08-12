package com.fov.domain.models.authentication.password
import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    val email : String,
    val code : String,
    val password: String
)
