package com.fov.domain.models.music.album

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class AlbumStream(
    @SerializedName("id") val id : String,
    @SerializedName("albumId") val songId : String,
)
