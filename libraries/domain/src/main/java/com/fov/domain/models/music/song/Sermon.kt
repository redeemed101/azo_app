package com.fov.domain.models.music.song


import androidx.annotation.Keep
import com.fov.domain.models.music.album.Album
import com.fov.domain.models.music.genre.Genre
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Sermon(
    @SerializedName("name") val name : String,
    @SerializedName("id") val id : String,
    @SerializedName("path") val path : String,
    @SerializedName("isTrending") val isTrending : Boolean,
    @SerializedName("previewPath") val previewPath : String,
    @SerializedName("artworkPath") val artworkPath : String,
    @SerializedName("description") val description : String,
    @SerializedName("genres") val genres : List<Genre>,
    @SerializedName("likes") val likes : List<SermonLike>,
    @SerializedName("streams") val streams : List<SermonStream>,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("publishedAt") val publishedAt : String,
    @SerializedName("series") val series : Album?,

    )
