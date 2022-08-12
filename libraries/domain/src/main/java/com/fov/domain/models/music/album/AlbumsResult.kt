package com.fov.domain.models.music.album
import com.fov.domain.models.music.album.Album
import kotlinx.serialization.Serializable


@Serializable
data class AlbumsResult(
    val albums  : List<Album> = emptyList()
)
