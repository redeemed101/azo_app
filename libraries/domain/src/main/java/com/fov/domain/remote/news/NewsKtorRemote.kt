package com.fov.domain.remote.news

import com.fov.domain.models.news.NewsResult

class NewsKtorRemote constructor(
   private val newsKtorService: NewsKtorService
) : NewsRemote
{
    override suspend fun getNews(page: Int) = newsKtorService.getNews(page)
}