package com.fov.domain.repositories.video

import com.fov.domain.models.music.video.VideosResult
import com.fov.domain.models.shorts.ShortsResult

interface VideoRepository {
    suspend fun getVideos(token:String,page: Int) : VideosResult?
    suspend fun getShorts(token:String,page: Int) : ShortsResult?
}