package com.fov.domain.repositories.video

import com.fov.domain.models.music.video.VideosResult
import com.fov.domain.models.shorts.ShortsResult
import com.fov.domain.remote.video.VideoRemote

class VideoRepositoryImpl constructor(
    private val videoRemote: VideoRemote
) : VideoRepository
{
    override suspend fun getVideos(token:String,page: Int) = videoRemote.getVideos(token,page)
    override suspend fun getShorts(token:String,page: Int) = videoRemote.getShorts(token,page)
}