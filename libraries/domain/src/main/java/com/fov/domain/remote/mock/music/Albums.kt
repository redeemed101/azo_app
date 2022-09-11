package com.fov.domain.remote.mock.music

import com.fov.domain.models.music.album.Album
import com.fov.domain.models.music.album.AlbumsResult
import com.fov.domain.models.music.song.Song
import com.google.gson.Gson

object AlbumsMockResponse {
    operator fun invoke(): String {
            val obj = AlbumsResult(
                albums = List(20) {
                    Album(
                        name = "Series $it",
                        id = "1234",
                        path = "",
                        artworkPath = "https://picsum.photos/200",
                        description = "This is an Album",
                        albumGenres = listOf(),
                        albumLikes = listOf(),
                        albumStreams = listOf(),
                        songs = List(10) {
                            Song(
                                name = "sermon $it",
                                id = "$it",
                                path = "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3",
                                artworkPath = "https://picsum.photos/200",
                                previewPath = "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3",
                                description = "",
                                songGenres = listOf(),
                                songLikes = listOf(),
                                songStreams = listOf()

                            )
                        }
                    )
                })



        return Gson().toJson(obj)
    }
}