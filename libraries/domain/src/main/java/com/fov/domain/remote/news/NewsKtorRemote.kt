package com.fov.domain.remote.news

import com.fov.domain.models.general.ImagePagerResult
import com.fov.domain.models.news.NewsResult

class NewsKtorRemote constructor(
   private val newsKtorService: NewsKtorService
) : NewsRemote
{
    override suspend fun getNews(page: Int) = newsKtorService.getNews(page)
    override suspend fun getNewsByYear(year:Int,page: Int) = newsKtorService.getNewsByYear(year,page)
    override suspend fun getImagePagers(page: Int) = newsKtorService.getImagePagers(page)
}