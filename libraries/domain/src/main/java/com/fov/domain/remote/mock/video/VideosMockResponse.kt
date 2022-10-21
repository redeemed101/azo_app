package com.fov.domain.remote.mock.video

import android.util.Log
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
                    artwork = "https://picsum.photos/200",
                    description = "",
                    artistName = "Apostle Ziba"
                )
            }
        )
        Log.d("json","${Gson().toJson(obj)}")
        return Gson().toJson(obj)
    }
}