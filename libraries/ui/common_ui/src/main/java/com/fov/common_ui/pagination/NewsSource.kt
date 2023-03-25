package com.fov.common_ui.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fov.common_ui.models.NewsModel
import com.fov.domain.interactors.news.NewsInteractor
import com.fov.domain.models.news.NewsResult

class NewsSource constructor(
    private val newsInteractor: NewsInteractor,
    private val queryType: String = "NORMAL",
    private val year:Int? = null
) : PagingSource<Int, NewsModel>() {
    override fun getRefreshKey(state: PagingState<Int, NewsModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsModel> {
        return try {
            val nextPage = params.key ?: 1
            var result : NewsResult?
            if(queryType == "BY_YEAR") {
                if (year != null)
                    result = newsInteractor.getNewsByYear(year,nextPage)
                else
                    result = newsInteractor.getNews(nextPage)
            }
            else{
                result = newsInteractor.getNews(nextPage)
            }
            val news = result?.news?.map {
                NewsModel(
                    title = it.title,
                    mainImage = it.mainImage.path,
                    images = it.images.map {
                        it.path
                    },
                    summary = it.content,
                    url = it.url
                )
            }
            if(news != null){
                LoadResult.Page(
                    data = news!!,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = nextPage.plus(1)
                )
            }
            else{
                LoadResult.Error(Exception(""))
            }
        }
        catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}