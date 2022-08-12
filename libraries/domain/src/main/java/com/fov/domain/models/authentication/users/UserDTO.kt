package com.fov.domain.models.authentication.users
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val email: String,
    val emailConfirmed: Boolean,
    val fullName: String,
    val id: String,
    val numFollowers: Int,
    val numFollowing: Int,
    val phoneNumber: String,
    val privateKey: String,
    val profile: String,
    val profilePicPath: String,
    val publicKey: String,
    val userName: String,
    val isClaimed:Boolean,
    val category : String,
    val location : String,
    val website : String,
    val dob : String,
    val gender : String,

)