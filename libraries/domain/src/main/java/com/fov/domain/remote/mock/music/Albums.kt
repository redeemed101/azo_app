package com.fov.domain.remote.mock.music

import com.fov.domain.models.music.album.Album
import com.fov.domain.models.music.album.AlbumsResult
import com.fov.domain.models.music.song.Song
import com.google.gson.Gson

object AlbumsMockResponse {
    operator fun invoke(): String {
            val obj = AlbumsResult(
                albums = listOf(
                    Album(
                        name = "Bob James",
                        id = "1234",
                        path = "",
                        artworkPath = "https://picsum.photos/200",
                        description = "This is an Album",
                        albumGenres = listOf(),
                        albumLikes = listOf(),
                        albumStreams = listOf(),
                        songs = List(10){
                            Song(
                                name = "song$it",
                                id = "$it",
                                path = "https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_700KB.mp3",
                                artworkPath = "https://picsum.photos/200",
                                previewPath = "https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_700KB.mp3",
                                description = "",
                                songGenres = listOf(),
                                songLikes = listOf(),
                                songStreams = listOf()

                            )
                        }
                    )
                )

            )
        return Gson().toJson(obj)
    }
}