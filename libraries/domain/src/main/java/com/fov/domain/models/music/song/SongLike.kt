package com.fov.domain.models.music.song
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class SongLike(
    @SerializedName("id") val id : String,
    @SerializedName("userId") val userId : String,
    @SerializedName("songId") val songId : String,
)
