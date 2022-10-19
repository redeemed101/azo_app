package com.fov.domain.remote.video

import com.fov.domain.models.music.video.VideosResult
import com.fov.domain.models.shorts.ShortsResult

class VideoKtorRemote constructor(
    private val videoKtorService: VideoKtorService
) : VideoRemote {
    override suspend fun getVideos(page: Int) = videoKtorService.getVideos(page)
    override suspend fun getShorts(page: Int) = videoKtorService.getShorts(page)
}