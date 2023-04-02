package com.fov.domain.remote.mock.music


import com.fov.domain.models.music.song.Sermon
import com.fov.domain.models.music.song.SermonsResult
import com.google.gson.Gson


object SongMockResponse {
    operator fun invoke(): String {
        val obj = Sermon(
            name = "Sermon 1",
            id = "1234",
            path = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
            artworkPath = "https://picsum.photos/200",
            previewPath = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
            description = "",
            createdAt = "",
            publishedAt ="",
            genres = emptyList(),
            likes = emptyList(),
            streams = emptyList(),
            series = null,
            isTrending = true
        )
        return Gson().toJson(obj)
    }
}
object SongsMockResponse {
    operator fun invoke(): String {

        val obj = SermonsResult(
            sermons =  List(20) {
                Sermon(
                    name = "Sermon $it",
                    id = "1234$it",
                    path = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                    artworkPath = "https://picsum.photos/200",
                    previewPath = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                    description = "",
                    genres = emptyList(),
                    likes = emptyList(),
                    streams = emptyList(),
                    createdAt = "",
                    publishedAt = "",
                    series = null,
                    isTrending = true

                )
            })
                return Gson().toJson(obj)
            }

    }
