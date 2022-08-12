package com.fov.domain.models.authentication.login
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResult(
    val token : String,
    val refreshToken : String
)