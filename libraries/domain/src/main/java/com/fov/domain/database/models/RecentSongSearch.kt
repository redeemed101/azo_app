package com.fov.domain.database.models

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
@Entity
@Immutable
@Serializable
data class RecentSongSearch(
    @PrimaryKey(autoGenerate = true) val dbId: Long = Long.MIN_VALUE,
    val songId : String,
    val artistName : String,
    val songName : String,
    val songLength : String,
    val artwork : String,
    val description : String ,
    val previewPath : String ,
    val path : String ,
    val likes : Int ,
    val streams : Int
)
