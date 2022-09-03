package com.fov.domain.remote.mock.video

import com.fov.domain.models.music.video.Video
import com.fov.domain.models.music.video.VideosResult
import com.google.gson.Gson

object VideosMockResponse {
    operator fun invoke(): String {
        val obj = VideosResult(
            videos = List(20) {
                Video(
                    videoId = "HzeK7g8cD0Y",
                    genres = emptyList(),
                    videoName = "Video number $it",
                    artwork = "/data/data/com.fov.azo/files/apostle.jpg",
                    description = "",
                    artistName = "Apostle Ziba"
                )
            }
        )
        return Gson().toJson(obj)
    }
}