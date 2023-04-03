package com.fov.domain.remote.video

import com.fov.domain.models.music.video.VideosResult
import com.fov.domain.models.shorts.ShortsResult

class VideoKtorRemote constructor(
    private val videoKtorService: VideoKtorService
) : VideoRemote {
    override suspend fun getVideos(token:String,page: Int) = videoKtorService.getVideos(token,page)
    override suspend fun getShorts(token:String,page: Int) = videoKtorService.getShorts(token,page)
}