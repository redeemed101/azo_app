package com.fov.domain.models.music.video

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class VideosResult(
    val videos : List<Video> = emptyList()
)