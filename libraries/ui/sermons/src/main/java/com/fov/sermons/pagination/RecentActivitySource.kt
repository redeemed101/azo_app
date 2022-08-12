package com.fov.sermons.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fov.domain.database.models.RecentActivity
import com.fov.domain.interactor.music.MusicInteractor

class RecentActivitySource constructor(
    private val musicInteractor: MusicInteractor,
) : PagingSource<Int, RecentActivity>() {
    override fun getRefreshKey(state: PagingState<Int, RecentActivity>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecentActivity> {
        return try {
            val nextPage = params.key ?: 1
            val result = musicInteractor.recentActivities()
            LoadResult.Page(
                data = result,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage.plus(1)
            )
        }
        catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}