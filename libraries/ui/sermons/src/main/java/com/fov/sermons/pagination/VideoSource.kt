package com.fov.sermons.pagination


import android.util.Log
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
        Log.d("XXX","In Video Source")
        return try {
            val nextPage = params.key ?: 1
            val videoResult = videoInteractor.getVideos(nextPage)
            Log.d("XXX","${videoResult?.videos?.get(0)?.artwork}")
            val videos = videoResult?.videos?.map {
                Video(
                    videoId = it.videoId,
                    genres = it.genres,
                    videoName = it.videoName,
                    artwork = it.artwork,
                    description = it.description,
                    artistName = it.artistName

                )
            }
            Log.d("XXX","In Video Source---")
            //return flowOf(PagingData.from(VIDEOS))
            LoadResult.Page(
                data = videos!!,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage.plus(1)
            )
        }
        catch (e: Exception) {
            Log.d("XXX","In Video Source ${e.message}")
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

}