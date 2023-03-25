package com.fov.domain.repositories.news

import com.fov.domain.models.general.ImagePagerResult
import com.fov.domain.models.news.NewsResult

interface NewsRepository {
    suspend fun getNews(page: Int) : NewsResult?
    suspend fun getNewsByYear(year:Int,page: Int): NewsResult?
    suspend fun getImagePager(page: Int) : ImagePagerResult?
}