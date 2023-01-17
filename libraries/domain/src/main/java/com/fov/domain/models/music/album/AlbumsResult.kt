package com.fov.domain.models.music.album
import com.fov.domain.models.music.album.Album
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class AlbumsResult(
    @SerializedName("albums") val albums  : List<Album> = emptyList()
)
