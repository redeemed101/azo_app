package com.fov.sermons.pagination

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fov.domain.interactors.video.VideoInteractor
import com.fov.sermons.mock.data.videos.VIDEOS
import com.fov.sermons.models.Video
import kotlinx.coroutines.flow.flowOf


class VideoSource constructor(
    private val videoInteractor : VideoInteractor
) : PagingSource<Int, Video>(){

    override fun getRefreshKey(state: PagingState<Int, Video>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Video> {

        return try {
            val nextPage = params.key ?: 1
            val videos = videoInteractor.getVideos(nextPage)
            //return flowOf(PagingData.from(VIDEOS))
            LoadResult.Page(
                data = VIDEOS,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage.plus(1)
            )
        }
        catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}