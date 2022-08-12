package com.fov.domain.models.music.album


import com.fov.domain.models.music.genre.Genre
import com.fov.domain.models.music.song.Song
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class Album(
    @SerializedName("name") val name : String,
    @SerializedName("id") val id : String,
    @SerializedName("path") val path : String,
    @SerializedName("artworkPath") val artworkPath : String,
    @SerializedName("description") val description : String,
    @SerializedName("albumGenres") val albumGenres : List<Genre> = emptyList(),
    @SerializedName("albumLikes") val albumLikes : List<AlbumLike>,
    @SerializedName("albumStreams") val albumStreams : List<AlbumStream>,
    @SerializedName("songs") val songs : List<Song>
)
