package com.fov.domain.database.models

import androidx.compose.runtime.Immutable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.serialization.Serializable

@Entity
@Immutable
@Serializable
data class DownloadedAlbum(
    @PrimaryKey(autoGenerate = true)
    val dbAlbumId: Long = 0,
    val albumName : String,
    val albumPath : String,
    val albumId : String,
    val artistName : String,
    val imagePath : String,
)


data class AlbumWithSongs(
    @Embedded val album: DownloadedAlbum,
    @Relation(
        parentColumn = "dbAlbumId",
        entityColumn = "parentAlbumId"
    )
    val songs: List<DownloadedSong>
)
