package com.fov.domain.models.music.album

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class AlbumLike(
    @SerializedName("id") val id : String,
    @SerializedName("userId") val userId : String,
    @SerializedName("albumId") val songId : String,
)
