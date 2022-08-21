package com.fov.domain.remote.mock.music


import com.fov.domain.models.music.song.Song
import com.fov.domain.models.music.song.SongsResult
import com.google.gson.Gson


object SongMockResponse {
    operator fun invoke(): String {
        val obj = Song(
            name = "Lewis James",
            id = "1234",
            path = "https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_700KB.mp3",
            artworkPath = "https://picsum.photos/200",
            previewPath = "https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_700KB.mp3",
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
            songs = listOf(Song(
                name = "Lewis James",
                id = "1234",
                path = "https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_700KB.mp3",
                artworkPath = "https://picsum.photos/200",
                previewPath = "https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_700KB.mp3",
                description = "",
                songGenres = emptyList(),
                songLikes = emptyList(),
                songStreams = emptyList()

            ))
        )
        return Gson().toJson(obj)

    }
}