package com.fov.domain.models.music.song
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SongStream(
    @SerializedName("id") val id : String,
    @SerializedName("sermonId") val sermonId : String,
)
