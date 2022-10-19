package com.fov.domain.models.news

import kotlinx.serialization.Serializable

@Serializable
data class NewsResult(
    val news : List<News> = emptyList()
)
