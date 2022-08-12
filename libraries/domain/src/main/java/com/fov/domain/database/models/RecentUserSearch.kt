package com.fov.domain.database.models

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Immutable
@Serializable
data class RecentUserSearch(
    @PrimaryKey(autoGenerate = true) val dbId: Long = Long.MIN_VALUE,
    val name: String,
    val email : String,
    val profile:String,
    val userName: String,
    val profilePic: String ,
    val phoneNumber:String,
    val id: String,
    val numFollowers : Int,
    val numFollowing : Int
)