package com.fov.domain.remote.news

import com.fov.domain.models.news.NewsResult

interface NewsRemote {
    suspend fun getNews(page: Int) : NewsResult?
}