package com.fov.domain.remote.video

import com.fov.domain.models.music.video.VideosResult

interface VideoRemote {
    suspend fun getVideos(page: Int): VideosResult?
}