package com.fov.domain.models.general

import com.fov.domain.BuildConfig
import com.fov.domain.models.news.NewsImage
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ImagePager(
    @SerializedName("id") val id : String,
    @SerializedName("path") var path : String,
    @SerializedName("description") val description : String,
    @SerializedName("show") val show : Boolean,
    @SerializedName("createdAt") val createdAt : String,
){
    object ModelMapper {
        fun withFullUrls(image : ImagePager) : ImagePager {
            image.path = "${BuildConfig.URL_PROTOCOL}${BuildConfig.FOV_URL}/${image.path}"

            return image
        }
    }
}
