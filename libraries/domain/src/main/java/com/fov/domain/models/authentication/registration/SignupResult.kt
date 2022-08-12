package com.fov.domain.models.authentication.registration

import com.fov.domain.database.models.User
import kotlinx.serialization.Serializable

@Serializable
data class SignupResult(
    val success : Boolean,
    val token : String,
    val refreshToken : String,
    val user : User
)