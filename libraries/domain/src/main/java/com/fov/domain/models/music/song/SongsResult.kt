package com.fov.domain.models.music.song
import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SongsResult(
   val sermons : List<Sermon> = emptyList()
)