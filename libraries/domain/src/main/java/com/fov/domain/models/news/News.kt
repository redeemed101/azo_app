package com.fov.domain.models.news

import com.fov.domain.BuildConfig
import com.fov.domain.models.general.ImagePager
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
){
    object ModelMapper {
        fun withFullUrls(news : News) : News {
             news.mainImage.path = "${BuildConfig.URL_PROTOCOL}${BuildConfig.FOV_URL}/${news.mainImage.path}"
             news.images = news.images.map {
                 var image = it
                 image.path = "${BuildConfig.URL_PROTOCOL}${BuildConfig.FOV_URL}/${news.mainImage.path}"
                 image
             }
            return news
        }
    }
}