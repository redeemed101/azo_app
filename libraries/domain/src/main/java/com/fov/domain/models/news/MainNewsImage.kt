package com.fov.domain.models.news

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MainNewsImage(
    @SerializedName("id") var id     : String,
    @SerializedName("newsId") var newsId : String,
    @SerializedName("path") var path   : String
)
