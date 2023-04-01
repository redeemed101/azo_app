package com.fov.domain.models.music.album

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class SeriesStream(
    @SerializedName("id") val id : String,
    @SerializedName("seriesId") val seriesId : String,
)
