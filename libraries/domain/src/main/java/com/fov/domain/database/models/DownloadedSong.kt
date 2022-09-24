package com.fov.domain.database.models

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Immutable
@Serializable
data class DownloadedSong(
    @PrimaryKey(autoGenerate = true)
    val dbId: Long = 0,
    val songName : String,
    val songPath : String,
    val songId : String,
    val artistName : String,
    val imagePath : String
)
