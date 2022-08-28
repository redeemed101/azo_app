package com.fov.sermons.mock.data.videos

import com.fov.sermons.models.Video

val VIDEOS = List(20) {
    Video(
        videoId = "HzeK7g8cD0Y",
         genres = emptyList(),
         videoName = "Video number $it",
         artwork = "/data/data/com.fov.azo/files/apostle.jpg",
         description  = "",
    )
}
