package com.fov.domain.models.music.album


import com.fov.domain.BuildConfig
import com.fov.domain.models.music.genre.Genre
import com.fov.domain.models.music.song.Sermon
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class Album(
    @SerializedName("name") val name : String,
    @SerializedName("id") val id : String,
    @SerializedName("path") val path : String,
    @SerializedName("isTrending") val isTrending : Boolean,
    @SerializedName("artworkPath") val artworkPath : String,
    @SerializedName("description") val description : String,
    @SerializedName("seriesGenres") val seriesGenres : List<Genre> = emptyList(),
    @SerializedName("likes") val likes : List<SeriesLike>,
    @SerializedName("streams") val streams : List<SeriesStream>,
    @SerializedName("sermons") val sermons : List<Sermon>
)

