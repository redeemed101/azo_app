package com.fov.domain.interactors.video

import com.fov.domain.models.music.video.VideosResult
import com.fov.domain.models.shorts.Short
import com.fov.domain.models.shorts.ShortsResult
import com.fov.domain.remote.mock.shorts.SHORTS
import com.fov.domain.repositories.video.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoInteractor constructor(
 private val videoRepository: VideoRepository
) {

    suspend fun  getVideos(page: Int) : VideosResult? = withContext(Dispatchers.IO) {
            videoRepository.getVideos(page)
    }
    suspend fun getShorts(page: Int) : ShortsResult? =  withContext(Dispatchers.IO) {

        videoRepository.getShorts(page)
    }

}