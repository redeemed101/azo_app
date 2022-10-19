package com.fov.domain.interactors.news

import com.fov.domain.repositories.news.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsInteractor constructor(
    private val newsRepository: NewsRepository
) {
    suspend fun getNews(page: Int) = withContext(Dispatchers.IO) {
        newsRepository.getNews(page)
    }
}