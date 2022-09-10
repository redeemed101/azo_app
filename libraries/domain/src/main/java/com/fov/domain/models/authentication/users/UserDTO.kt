package com.fov.domain.models.authentication.users
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val email: String,
    val emailConfirmed: Boolean,
    val isSubscribed: Boolean = false,
    val fullName: String,
    val id: String,
    val phoneNumber: String,
    val privateKey: String,
    val publicKey: String,

)