package com.fov.domain.remote.mock.music

import com.fov.domain.models.music.album.Album
import com.fov.domain.models.music.album.AlbumsResult
import com.fov.domain.models.music.song.Sermon
import com.google.gson.Gson

object AlbumsMockResponse {
    operator fun invoke(): String {
            val obj = AlbumsResult(
                albums = List(20) {
                    Album(
                        name = "Series $it",
                        id = "1234$it",
                        path = "",
                        artworkPath = "https://picsum.photos/200",
                        description = "This is an Album",
                        seriesGenres = listOf(),
                        likes = listOf(),
                        streams = listOf(),
                        isTrending = true,
                        sermons = List(10) {
                            Sermon(
                                name = "sermon $it",
                                id = "$it",
                                path = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                                artworkPath = "https://picsum.photos/200",
                                previewPath = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                                description = "",
                                genres = listOf(),
                                likes = listOf(),
                                streams = listOf(),
                                isTrending = true,
                                series = null,
                                createdAt = "",
                                publishedAt = ""
                            )
                        }
                    )
                })



        return Gson().toJson(obj)
    }
}