package com.fov.domain.remote.apollo.music



import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.fov.domain.music.*
import com.fov.domain.albums.GetAlbumQuery
import com.fov.domain.albums.GetAlbumsPaginatedQuery
import com.fov.domain.genres.*
import com.fov.domain.remote.apollo.ApolloSetup
import com.fov.domain.remote.apollo.music.ApolloMusicService
import com.fov.domain.songs.GetSongsByYearPaginatedQuery
import com.fov.domain.songs.GetSongsPaginatedQuery
import com.fov.domain.songs.GetTrendingSongsPaginatedQuery

class ApolloMusicServiceImpl constructor(
    private val apolloSetup : ApolloSetup
) : ApolloMusicService {
    private var apolloClient: ApolloClient = apolloSetup.setUpApolloClient("api/genreql")
    //private val  baseUrl = BuildConfig.fovMusicApiUrl

    override suspend fun  getGenres(token: String): GetGenresQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/genreql",token)
        val res =  apolloClient.query(
            GetGenresQuery()
        ).execute()
        return res.data
    }
    override suspend fun  getGenreSongsPaginated(token: String,id:String, page:Int, size:Int): GetGenreSongsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/genreql", token)
        val res =  apolloClient.query(
            GetGenreSongsPaginatedQuery(Optional.presentIfNotNull(id),
                Optional.presentIfNotNull(page),
                Optional.presentIfNotNull(size))
        ).execute()
        return res.data
    }
    override suspend fun  getGenreAlbumsPaginated(token: String,id:String, page:Int, size:Int): GetGenreAlbumsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/genreql",token)
        val res =  apolloClient.query(
            GetGenreAlbumsPaginatedQuery( Optional.presentIfNotNull(id),
                Optional.presentIfNotNull(page),
                Optional.presentIfNotNull(size))

        ).execute()
        return res.data
    }



    override suspend fun getGenre(token: String,id:String): GetGenreQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/genreql",token)
        val res =  apolloClient.query(
            GetGenreQuery( Optional.presentIfNotNull(id))
        ).execute()
        return res.data
    }
    override suspend fun getUserLikedSongs(token: String,id:String): GetUserLikedSongsQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/userql",token)
        val res = apolloClient.query(
            GetUserLikedSongsQuery( Optional.presentIfNotNull(id))

        ).execute()
        return res.data
    }

    override suspend fun getSongsPaginated(token: String,page: Int, size: Int
    ): GetSongsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/sermonql",token)
        val res = apolloClient.query(
            GetSongsPaginatedQuery(
                Optional.presentIfNotNull(page),
                Optional.presentIfNotNull(size)
            )

        ).execute()
        return res.data
    }

    override suspend fun getTrendingSongsPaginated(
        token: String,
        page: Int,
        size: Int
    ): GetTrendingSongsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/sermonql",token)
        val res = apolloClient.query(
            GetTrendingSongsPaginatedQuery(
                Optional.presentIfNotNull(page),
                Optional.presentIfNotNull(size)
            )

        ).execute()
        return res.data
    }

    override suspend fun getSongsByYearPaginated(
        token: String,
        year: Int,
        page: Int,
        size: Int
    ): GetSongsByYearPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/sermonql",token)
        val res = apolloClient.query(
            GetSongsByYearPaginatedQuery(
                Optional.presentIfNotNull(year),
                Optional.presentIfNotNull(page),
                Optional.presentIfNotNull(size)
            )

        ).execute()
        return res.data
    }

    override suspend fun getUserLikedSongsPaginated(
        token: String,
        id: String,
        page: Int,
        size: Int
    ): GetUserLikedSongsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/userql",token)
        val res = apolloClient.query(
            GetUserLikedSongsPaginatedQuery( Optional.presentIfNotNull(id) ,
                Optional.presentIfNotNull(page),
                Optional.presentIfNotNull(size))

        ).execute()
        return res.data
    }



    override suspend fun  getAlbumsPaginated(token: String,page:Int, size:Int): GetAlbumsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/albumql",token)
        val res = apolloClient.query(
            GetAlbumsPaginatedQuery(
                Optional.presentIfNotNull(page),
                Optional.presentIfNotNull(size))

        ).execute()
        return res.data
    }

    override suspend fun getUserLikedAlbumsPaginated(
        token: String,
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

    override suspend fun getAlbum(token: String,id:String): GetAlbumQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("api/albumql")
        val res = apolloClient.query(
            GetAlbumQuery( Optional.presentIfNotNull(id))

        ).execute()
        return  res.data
    }


}