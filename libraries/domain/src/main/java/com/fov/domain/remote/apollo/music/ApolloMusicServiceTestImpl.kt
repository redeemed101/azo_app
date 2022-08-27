package com.fov.domain.remote.apollo.music

import com.apollographql.apollo3.ApolloClient
import com.fov.domain.music.*
import com.fov.domain.albums.GetAlbumQuery
import com.fov.domain.albums.GetAlbumsPaginatedQuery
import com.fov.domain.genres.*
import com.fov.domain.remote.apollo.ApolloSetup
import com.fov.domain.remote.apollo.music.ApolloMusicService

class ApolloMusicServiceTestImpl constructor(
    private val apolloSetup : ApolloSetup
) : ApolloMusicService {
    private var apolloClient: ApolloClient = apolloSetup.setUpTestApolloClient()

    override suspend fun  getGenres(): GetGenresQuery.Data? {

        return GetGenresQuery.Data(
            List(5){
                GetGenresQuery.Genre(
                    "$it",
                    "Category $it",
                )
            }
        )
    }
    override suspend fun  getGenreSongsPaginated(id:String, page:Int, size:Int): GetGenreSongsPaginatedQuery.Data? {

        return GetGenreSongsPaginatedQuery.Data(
            GetGenreSongsPaginatedQuery.Genre(
                "1234",
                "Category",
            ),
            List(20){ num ->
                GetGenreSongsPaginatedQuery.SongsPaginated(
                    "1234",
                    "Sermon",
                    "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                    emptyList(),
                    "https://picsum.photos/id/$num/200",

                )
            }
        )
    }
    override suspend fun  getGenreAlbumsPaginated(id:String, page:Int, size:Int): GetGenreAlbumsPaginatedQuery.Data? {
        return GetGenreAlbumsPaginatedQuery.Data(
            GetGenreAlbumsPaginatedQuery.Genre(
                "1234",
                "Category 1",
            ),
            List(20){ num ->
                GetGenreAlbumsPaginatedQuery.AlbumsPaginated(
                    "Series $num",
                    "$num",
                    "https://picsum.photos/id/$num/200",
                    "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                    emptyList(),
                    List(10) { num ->
                        GetGenreAlbumsPaginatedQuery.Song(
                            "$num",
                            "Record",
                            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                            "https://picsum.photos/id/$num/200",

                        emptyList(),
                        emptyList(),
                        emptyList(),
                        )
                    },

                )
            }
        )

    }



    override suspend fun getGenre(id:String): GetGenreQuery.Data? {

      return GetGenreQuery.Data(
          GetGenreQuery.Genre(
              "1234",
              "Category 1",
          ),
          List(2){ num ->
              GetGenreQuery.Song(
                  "1234",
                  "Sermon $num",
                  "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                  "https://picsum.photos/id/$num/200",
                  emptyList(),
                  emptyList(),
                  List(3){
                      GetGenreQuery.Genre1(
                          "Category",
                          "$it",
                      )
                  }
              )

          },
          List(20){ num ->
              GetGenreQuery.Album(
                  "Album",
                  "$num",
                  "Name",
              )
          }

      )
    }
    override suspend fun getUserLikedSongs(id:String): GetUserLikedSongsQuery.Data? {

        return GetUserLikedSongsQuery.Data(
            List(20){ num ->
                GetUserLikedSongsQuery.LikedSong(
                    "1234",
                    "Sermon $num",
                    "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                    "https://picsum.photos/id/$num/200",

                    emptyList(),
                    emptyList(),
                    List(3){
                        GetUserLikedSongsQuery.Genre(
                            "1234",
                            "Category $it",
                        )
                    }
                )

            }
        )

    }

    override suspend fun getUserLikedSongsPaginated(
        id: String,
        page: Int,
        size: Int
    ): GetUserLikedSongsPaginatedQuery.Data? {
        return GetUserLikedSongsPaginatedQuery.Data(
            List(20){ num ->
                GetUserLikedSongsPaginatedQuery.LikedSongsPaginated(
                    "1234",
                    "Sermon $num",
                    "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                    "https://picsum.photos/id/$num/200",
                    emptyList(),
                    emptyList(),
                    List(3){
                        GetUserLikedSongsPaginatedQuery.Genre(
                            "Genre",
                            "$it",
                        )
                    }
                )

            }
        )
    }


    override suspend fun  getAlbumsPaginated(page:Int, size:Int): GetAlbumsPaginatedQuery.Data? {
        return GetAlbumsPaginatedQuery.Data(
            List(20) {
                GetAlbumsPaginatedQuery.AlbumsPaginated(
                    "Album",
                    "$it",
                    "https://picsum.photos/id/$it/200",
                    "kkkk",
                    emptyList(),
                    List(10) {
                        GetAlbumsPaginatedQuery.Song(
                   "Song",
                            "12345",
                            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                            "https://picsum.photos/id/$it/200",
                            List(100){
                                GetAlbumsPaginatedQuery.Like1(
                                    "Like",
                                )
                            },
                            List(100){
                                GetAlbumsPaginatedQuery.Stream(
                                    "Stream",
                                )
                            },
                            List(5){
                                GetAlbumsPaginatedQuery.Genre(
                                    "Genre",
                                    "1234",
                                )
                            }
                        )
                    }
                )
            }
        )

    }

    override suspend fun getUserLikedAlbumsPaginated(
        id: String,
        page: Int,
        size: Int
    ): GetUserLikedAlbumsPaginatedQuery.Data? {
        return GetUserLikedAlbumsPaginatedQuery.Data(
            List(20) {
                GetUserLikedAlbumsPaginatedQuery.LikedAlbumsPaginated(
                    "21",
                    "https://picsum.photos/id/$it/200",
                    "Series $it",
                    "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                    List(100){
                        GetUserLikedAlbumsPaginatedQuery.Like(
                            "L1",
                        )
                    },
                    List(10) {
                        GetUserLikedAlbumsPaginatedQuery.Song(
                            "Song",
                            "12345",
                            "This$it",
                            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",

                            List(100){
                                GetUserLikedAlbumsPaginatedQuery.Like1(
                                    "1",
                                )
                            },
                            List(100){
                                GetUserLikedAlbumsPaginatedQuery.Stream(
                                    "Stream",
                                )
                            },
                            List(5){
                                GetUserLikedAlbumsPaginatedQuery.Genre(
                                    "Genre",
                                    "1234",
                                )
                            }
                        )
                    },
                    List(5){
                        GetUserLikedAlbumsPaginatedQuery.Genre1(
                            "Genre",
                            "1234",
                        )
                    },
                    List(100){
                        GetUserLikedAlbumsPaginatedQuery.Stream1(
                            "Stream",
                        )
                    },
                )
            }
        )
    }

    override suspend fun getAlbum(id:String): GetAlbumQuery.Data? {


        return GetAlbumQuery.Data(
            GetAlbumQuery.Album(
                "1234",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                "Category 1",
                "kkkk",
                emptyList(),
                emptyList(),
                emptyList(),
                emptyList()
            )

        )

    }

}