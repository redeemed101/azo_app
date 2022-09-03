package com.fov.domain.remote.video

import com.fov.domain.models.music.video.VideosResult

class VideoKtorRemote constructor(
    private val videoKtorService: VideoKtorService
) : VideoRemote {
    override suspend fun getVideos(page: Int) = videoKtorService.getVideos(page)
}