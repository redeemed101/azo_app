package com.fidarr.domain.remote.mock.music

import com.fidarr.domain.models.music.album.Album
import com.fidarr.domain.models.music.album.AlbumsResult
import com.fidarr.domain.models.music.artist.Artist
import com.fidarr.domain.models.music.song.Song
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
                        artist = Artist(
                            name = "Madam James",
                            isProfileClaimed = false,
                            id = "1234",
                            imagePath = "https://picsum.photos/200",
                            artistGenres = listOf(),
                        ),
                        albumGenres = listOf(),
                        featuringArtists = listOf(),
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
                                artist = Artist(
                                    name = "",
                                    isProfileClaimed = false,
                                    id = "",
                                    imagePath = "https://picsum.photos/200",
                                    artistGenres = listOf(),
                                ),
                                songGenres = listOf(),
                                featuringArtists = listOf(),
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