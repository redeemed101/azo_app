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
                    "Genre",
                )
            }
        )
    }
    override suspend fun  getGenreSongsPaginated(id:String, page:Int, size:Int): GetGenreSongsPaginatedQuery.Data? {

        return GetGenreSongsPaginatedQuery.Data(
            GetGenreSongsPaginatedQuery.Genre(
                "1234",
                "Genre",
            ),
            List(20){ num ->
                GetGenreSongsPaginatedQuery.SongsPaginated(
                    "Song",
                    "$num",
                    "Record",
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
                "Genre",
            ),
            List(20){ num ->
                GetGenreAlbumsPaginatedQuery.AlbumsPaginated(
                    "Album",
                    "$num",
                    "https://picsum.photos/id/$num/200",
                    "https://picsum.photos/id/$num/200",
                    emptyList(),
                    List(10) { num ->
                        GetGenreAlbumsPaginatedQuery.Song(
                            "$num",
                            "Record",
                            "",
                            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",

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
              "Genre",
              "1234",
          ),
          List(2){ num ->
              GetGenreQuery.Song(
                  "Song",
                  "$num",
                  "Record",
                  "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                  emptyList(),
                  emptyList(),
                  List(3){
                      GetGenreQuery.Genre1(
                          "Genre",
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
                    "Song",
                    "$num",
                    "Record",
                    "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",

                    emptyList(),
                    emptyList(),
                    List(3){
                        GetUserLikedSongsQuery.Genre(
                            "Genre",
                            "$it",
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
                    "Song",
                    "$num",
                    "Record",
                    "",
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
                    "Lewis",
                    "kkkk",
                    emptyList(),
                    List(10) {
                        GetAlbumsPaginatedQuery.Song(
                   "Song",
                            "12345",
                            "This$it",
                            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
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
                    "Album",
                    "$it",
                    "Lewis",
                    "kkkk",
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
                "Album",
                "123",
                "Lewis",
                "kkkk",
                emptyList(),
                emptyList(),
                emptyList(),
                emptyList()
            )

        )

    }

}