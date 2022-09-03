package com.fov.domain.repositories.video

import com.fov.domain.models.music.video.VideosResult

interface VideoRepository {
    suspend fun getVideos(page: Int) : VideosResult?
}