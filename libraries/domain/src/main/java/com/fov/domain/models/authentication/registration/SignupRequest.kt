package com.fov.domain.models.authentication.registration
import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val userName: String,
    val fullName: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val profilePicPath : String? = null,
    val countryId: Int
)
