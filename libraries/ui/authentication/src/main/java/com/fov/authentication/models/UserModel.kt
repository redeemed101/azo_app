package com.fov.authentication.models

data class UserModel(
    val name: String,
    val email : String,
    val phoneNumber:String = "",
    val id: String,
    val isEmailConfirmed: Boolean = false,
)
