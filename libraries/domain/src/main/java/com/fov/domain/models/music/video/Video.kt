package com.fov.domain.models.music.video

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class Video(
    @SerializedName("videoId") val videoId : String,
    @SerializedName("genres") val genres: List<String> = listOf(),
    @SerializedName("name") val videoName : String,
    @SerializedName("artwork") val artwork : String,
    @SerializedName("artistName") val artistName: String,
    @SerializedName("description") val description : String = "",
)
