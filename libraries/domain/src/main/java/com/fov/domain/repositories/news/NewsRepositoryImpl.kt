package com.fov.domain.repositories.news

import com.fov.domain.models.general.ImagePagerResult
import com.fov.domain.models.news.NewsResult
import com.fov.domain.remote.news.NewsRemote

class NewsRepositoryImpl constructor(
    private val newsRemote: NewsRemote
) : NewsRepository {
    override suspend fun getNews(page: Int) = newsRemote.getNews(page)
    override suspend fun getNewsByYear(year:Int,page: Int) = newsRemote.getNewsByYear(year,page)
    override suspend fun getImagePager(page: Int) = newsRemote.getImagePagers(page)

}