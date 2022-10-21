package com.fov.domain.remote.news

import com.fov.domain.models.general.ImagePagerResult
import com.fov.domain.models.news.NewsResult

interface NewsRemote {
    suspend fun getNews(page: Int) : NewsResult?
    suspend fun getImagePagers(page: Int) : ImagePagerResult?
}