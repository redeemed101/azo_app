package com.fov.domain.remote.apollo.music



import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.fov.domain.music.*
import com.fov.domain.albums.GetAlbumQuery
import com.fov.domain.albums.GetAlbumsPaginatedQuery
import com.fov.domain.genres.*
import com.fov.domain.remote.apollo.ApolloSetup
import com.fov.domain.remote.apollo.music.ApolloMusicService
import com.fov.domain.songs.GetSongsPaginatedQuery
import com.fov.domain.songs.GetTrendingSongsPaginatedQuery

class ApolloMusicServiceImpl constructor(
    private val apolloSetup : ApolloSetup
) : ApolloMusicService {
    private var apolloClient: ApolloClient = apolloSetup.setUpApolloClient("api/genreql")
    //private val  baseUrl = BuildConfig.fovMusicApiUrl

    override suspend fun  getGenres(): GetGenresQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/genreql")
        val res =  apolloClient.query(
            GetGenresQuery()
        ).execute()
        return res.data
    }
    override suspend fun  getGenreSongsPaginated(id:String, page:Int, size:Int): GetGenreSongsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/genreql")
        val res =  apolloClient.query(
            GetGenreSongsPaginatedQuery(Optional.presentIfNotNull(id),
                Optional.presentIfNotNull(page),
                Optional.presentIfNotNull(size))
        ).execute()
        return res.data
    }
    override suspend fun  getGenreAlbumsPaginated(id:String, page:Int, size:Int): GetGenreAlbumsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/genreql")
        val res =  apolloClient.query(
            GetGenreAlbumsPaginatedQuery( Optional.presentIfNotNull(id),
                Optional.presentIfNotNull(page),
                Optional.presentIfNotNull(size))

        ).execute()
        return res.data
    }



    override suspend fun getGenre(id:String): GetGenreQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/genreql")
        val res =  apolloClient.query(
            GetGenreQuery( Optional.presentIfNotNull(id))
        ).execute()
        return res.data
    }
    override suspend fun getUserLikedSongs(id:String): GetUserLikedSongsQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/userql")
        val res = apolloClient.query(
            GetUserLikedSongsQuery( Optional.presentIfNotNull(id))

        ).execute()
        return res.data
    }

    override suspend fun getSongsPaginated(page: Int, size: Int
    ): GetSongsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/sermonql")
        val res = apolloClient.query(
            GetSongsPaginatedQuery(
                Optional.presentIfNotNull(page),
                Optional.presentIfNotNull(size)
            )

        ).execute()
        return res.data
    }

    override suspend fun getTrendingSongsPaginated(
        page: Int,
        size: Int
    ): GetTrendingSongsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/sermonql")
        val res = apolloClient.query(
            GetTrendingSongsPaginatedQuery(
                Optional.presentIfNotNull(page),
                Optional.presentIfNotNull(size)
            )

        ).execute()
        return res.data
    }

    override suspend fun getUserLikedSongsPaginated(
        id: String,
        page: Int,
        size: Int
    ): GetUserLikedSongsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/userql")
        val res = apolloClient.query(
            GetUserLikedSongsPaginatedQuery( Optional.presentIfNotNull(id) ,
                Optional.presentIfNotNull(page),
                Optional.presentIfNotNull(size))

        ).execute()
        return res.data
    }



    override suspend fun  getAlbumsPaginated(page:Int, size:Int): GetAlbumsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/albumql")
        val res = apolloClient.query(
            GetAlbumsPaginatedQuery(
                Optional.presentIfNotNull(page),
                Optional.presentIfNotNull(size))

        ).execute()
        return res.data
    }

    override suspend fun getUserLikedAlbumsPaginated(
        id: String,
        page: Int,
        size: Int
    ): GetUserLikedAlbumsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/userql")
        val res = apolloClient.query(
            GetUserLikedAlbumsPaginatedQuery( Optional.presentIfNotNull(id),
                Optional.presentIfNotNull(page),
                Optional.presentIfNotNull(size))
        ).execute()
        return res.data
    }

    override suspend fun getAlbum(id:String): GetAlbumQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/albumql")
        val res = apolloClient.query(
            GetAlbumQuery( Optional.presentIfNotNull(id))

        ).execute()
        return  res.data
    }


}