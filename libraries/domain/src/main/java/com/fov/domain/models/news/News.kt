package com.fov.domain.models.news

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class News(
    @SerializedName("id") var id: String,
    @SerializedName("title") var title : String,
    @SerializedName("content") var content : String,
    @SerializedName("publishDate") var publishDate : String,
    @SerializedName("mainImage") var mainImage: MainNewsImage,
    @SerializedName("images") var images : List<NewsImage>,
    @SerializedName("createdAt") var createdAt: String,
    @SerializedName("url") var url: String
)