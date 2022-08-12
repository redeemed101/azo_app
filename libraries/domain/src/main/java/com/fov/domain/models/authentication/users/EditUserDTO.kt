package com.fov.domain.models.authentication.users

data class EditUserDTO(
    val category: String,
    val dob: String,
    val fullName: String,
    val userName : String,
    val gender: String,
    val location: String,
    val phoneNumber: String,
    val profile: String,
    val profilePicPath: String,
    val userId: String,
    val website: String,
    val email : String
)