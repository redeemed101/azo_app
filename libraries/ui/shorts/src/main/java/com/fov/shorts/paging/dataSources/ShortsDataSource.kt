package com.fov.shorts.paging.dataSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fov.domain.interactors.video.VideoInteractor
import com.fov.domain.models.shorts.Short

class ShortsDataSource constructor( private val videoInteractor: VideoInteractor, private val accessToken : String)
    : PagingSource<Int, Short>(){
    override fun getRefreshKey(state: PagingState<Int, Short>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Short> {
        return try {
            val nextPage = params.key ?: 1
            val shortsResult = videoInteractor.getShorts(accessToken,nextPage)
            val shorts = shortsResult!!.shorts.map { it ->
                  Short.ModelMapper.withFullUrls(it)
            }
            LoadResult.Page(
                data = shortsResult!!.shorts,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (shortsResult.shorts.isNullOrEmpty()) null else nextPage.plus(1)
            )
        }
        catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}