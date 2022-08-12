package com.fov.domain.models.authentication.login

import com.fov.domain.database.models.User
import kotlinx.serialization.Serializable


@Serializable
data class SigninResult(
    val success : Boolean,
    val token : String,
    val refreshToken : String,
    val user : User
)
