package com.fov.sermons.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fov.common_ui.models.NewsModel
import com.fov.domain.interactors.news.NewsInteractor
import com.fov.sermons.models.ImagePagerModel

class ImagePagerSource constructor(
    private val newsInteractor: NewsInteractor
) : PagingSource<Int, ImagePagerModel>() {
    override fun getRefreshKey(state: PagingState<Int, ImagePagerModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImagePagerModel> {

        return try {
            val nextPage = params.key ?: 1
            val result = newsInteractor.getImagePagers(nextPage)
            val news = result?.images?.map {
                ImagePagerModel(
                    id = it.id,
                    path = it.path,
                    description = it.description
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