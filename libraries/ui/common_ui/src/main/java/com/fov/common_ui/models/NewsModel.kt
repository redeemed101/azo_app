package com.fov.common_ui.models

data class NewsModel(
    val title : String,
    val mainImage : String,
    val images : List<String>,
    val summary: String,
    val url : String
){
    object ModelMapper {
        fun fromNewsDomainModel(news : com.fov.domain.models.news.News ) = NewsModel(
             title = news.title,
             mainImage = news.mainImage.path,
             images = news.images.map { img ->
                 img.path
             },
            summary = news.content,
            url = news.url
        )
    }
}
