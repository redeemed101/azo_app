package com.fov.domain.database.models

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Immutable
@Serializable
data class User(
    @PrimaryKey val dbId: Long = Long.MIN_VALUE,
    val name: String,
    val email : String,
    val profile:String,
    val privateKey: String = "",
    val publicKey: String = "",
    val profilePicPath: String = "",
    val id: String
) {

}