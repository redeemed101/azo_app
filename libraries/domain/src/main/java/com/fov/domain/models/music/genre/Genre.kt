package com.fov.domain.models.music.genre

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class Genre(
    @SerializedName("name") val name : String,
    @SerializedName("id") val id : String
)
