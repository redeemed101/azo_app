package com.fov.domain.remote.mock.music


import com.fov.domain.models.music.song.Song
import com.fov.domain.models.music.song.SongsResult
import com.google.gson.Gson


object SongMockResponse {
    operator fun invoke(): String {
        val obj = Song(
            name = "Lewis James",
            id = "1234",
            path = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
            artworkPath = "https://picsum.photos/200",
            previewPath = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
            description = "",

            songGenres = emptyList(),
            songLikes = emptyList(),
            songStreams = emptyList()
        )
        return Gson().toJson(obj)
    }
}
object SongsMockResponse {
    operator fun invoke(): String {

        val obj = SongsResult(
            songs =  List(20) {
                Song(
                    name = "Lewis James $it",
                    id = "1234",
                    path = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                    artworkPath = "https://picsum.photos/200",
                    previewPath = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                    description = "",
                    songGenres = emptyList(),
                    songLikes = emptyList(),
                    songStreams = emptyList()

                )
            })
                return Gson().toJson(obj)
            }

    }
