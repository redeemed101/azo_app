package com.fov.domain.models.news

import com.fov.domain.BuildConfig
import com.fov.domain.models.shorts.Short
import com.fov.domain.models.shorts.ShortType
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class NewsImage(
    @SerializedName("id") var id     : String,
    @SerializedName("newsId") var newsId : String,
    @SerializedName("path") var path   : String
){
    object ModelMapper {
        fun withFullUrls(image : NewsImage) : NewsImage {
            image.path = "${BuildConfig.URL_PROTOCOL}${BuildConfig.FOV_URL}/${image.path}"

            return image
        }
    }
}
