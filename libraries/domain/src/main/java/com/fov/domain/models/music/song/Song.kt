package com.fov.domain.models.music.song


import androidx.annotation.Keep
import com.fov.domain.models.music.genre.Genre
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Song(
    @SerializedName("name") val name : String,
    @SerializedName("id") val id : String,
    @SerializedName("path") val path : String,
    @SerializedName("previewPath") val previewPath : String,
    @SerializedName("artworkPath") val artworkPath : String,
    @SerializedName("description") val description : String,
    @SerializedName("songGenres") val songGenres : List<Genre>,
    @SerializedName("songLikes") val songLikes : List<SongLike>,
    @SerializedName("songStreams") val songStreams : List<SongStream>
)
