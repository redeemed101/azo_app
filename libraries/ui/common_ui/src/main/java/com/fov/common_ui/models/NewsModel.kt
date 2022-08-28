package com.fov.common_ui.models

data class NewsModel(
    val title : String,
    val mainImage : String,
    val images : List<String>,
    val summary: String,
    val url : String
)
