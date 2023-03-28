package com.fov.domain.models.authentication.registration

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.fov.domain.database.models.User
import kotlinx.serialization.Serializable

@Serializable
data class SignupResult(
    val success : Boolean,
    val token : String,
    val refreshToken : String,
    val user : UserResult
)
@Serializable
data class UserResult(
    val name: String,
    val email : String,
    val privateKey: String = "",
    val publicKey: String = "",
    val profilePicPath: String = "",
    val id: String
)