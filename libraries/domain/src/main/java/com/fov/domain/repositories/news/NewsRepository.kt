package com.fov.domain.repositories.news

import com.fov.domain.models.news.NewsResult

interface NewsRepository {
    suspend fun getNews(page: Int) : NewsResult?
}