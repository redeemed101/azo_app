package com.fov.domain.remote.video

import com.fov.domain.models.music.video.VideosResult
import com.fov.domain.models.shorts.ShortsResult

interface VideoRemote {
    suspend fun getVideos(token:String,page: Int): VideosResult?
    suspend fun getShorts(token:String,page: Int): ShortsResult?
}