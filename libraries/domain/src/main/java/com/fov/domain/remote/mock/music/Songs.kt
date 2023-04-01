package com.fov.domain.remote.mock.music


import com.fov.domain.models.music.song.Sermon
import com.fov.domain.models.music.song.SongsResult
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

            songGenres = emptyList(),
            likes = emptyList(),
            streams = emptyList()
        )
        return Gson().toJson(obj)
    }
}
object SongsMockResponse {
    operator fun invoke(): String {

        val obj = SongsResult(
            sermons =  List(20) {
                Sermon(
                    name = "Sermon $it",
                    id = "1234$it",
                    path = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                    artworkPath = "https://picsum.photos/200",
                    previewPath = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                    description = "",
                    songGenres = emptyList(),
                    likes = emptyList(),
                    streams = emptyList()

                )
            })
                return Gson().toJson(obj)
            }

    }
