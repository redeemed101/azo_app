package com.fov.domain.models.music.genre

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class Genre(
    @SerializedName("name") val name : String,
    @SerializedName("id") val id : String
)
