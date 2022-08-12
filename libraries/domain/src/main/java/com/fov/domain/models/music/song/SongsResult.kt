package com.fov.domain.models.music.song
import com.fov.domain.models.music.song.Song
import kotlinx.serialization.Serializable


@Serializable
data class SongsResult(
    val songs : List<Song> = emptyList()
)