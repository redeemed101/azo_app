package com.fov.domain.database.models

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Immutable
@Serializable
data class DownloadedAlbum(
    @PrimaryKey(autoGenerate = true)
    val dbId: Long = 0,
    val albumName : String,
    val albumPath : String,
    val albumId : String,
    val artistName : String,
    val imagePath : String,
)
