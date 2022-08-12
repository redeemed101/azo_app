package com.fov.domain.models.music.song


import com.fov.domain.models.music.genre.Genre
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class Song(
    @SerializedName("name") val name : String,
    @SerializedName("id") val id : String,
    @SerializedName("path") val path : String,
    @SerializedName("previewPath") val previewPath : String,
    @SerializedName("description") val description : String,
    @SerializedName("songGenres") val songGenres : List<Genre>,
    @SerializedName("songLikes") val songLikes : List<SongLike>,
    @SerializedName("songStreams") val songStreams : List<SongStream>
)