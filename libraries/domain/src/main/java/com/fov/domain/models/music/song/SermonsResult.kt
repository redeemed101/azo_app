package com.fov.domain.models.music.song
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class SermonsResult(
   @SerializedName("sermons") val sermons : List<Sermon> = emptyList()
)