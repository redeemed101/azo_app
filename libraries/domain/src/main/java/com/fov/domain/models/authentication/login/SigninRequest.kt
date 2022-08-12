package com.fov.domain.models.authentication.login
import kotlinx.serialization.Serializable

@Serializable
data class SigninRequest(
    val username: String,
    val password: String
)
