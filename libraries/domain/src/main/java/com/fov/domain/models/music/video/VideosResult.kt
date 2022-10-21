package com.fov.domain.models.music.video

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class VideosResult(
    @SerializedName("videos" ) val videos : List<Video> = emptyList()
)