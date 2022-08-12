package com.fov.domain.remote.apollo.music

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.fov.domain.music.*
import com.fov.domain.albums.GetAlbumQuery
import com.fov.domain.albums.GetAlbumsPaginatedQuery
import com.fov.domain.genres.*
import com.fov.domain.remote.apollo.ApolloSetup
import com.fov.domain.remote.apollo.music.ApolloMusicService

class ApolloMusicServiceImpl constructor(
    private val apolloSetup : ApolloSetup
) : ApolloMusicService {
    private var apolloClient: ApolloClient = apolloSetup.setUpApolloClient("music/artistql")
    //private val  baseUrl = BuildConfig.fovMusicApiUrl

    override suspend fun  getGenres(): GetGenresQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("music/genreql")
        val res =  apolloClient.query(
            GetGenresQuery
                .builder()
                .build()
        ).await()
        return res.data
    }
    override suspend fun  getGenreSongsPaginated(id:String, page:Int, size:Int): GetGenreSongsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("music/genreql")
        val res =  apolloClient.query(
            GetGenreSongsPaginatedQuery
                .builder()
                .id(id)
                .page(page)
                .size(size)
                .build()
        ).await()
        return res.data
    }
    override suspend fun  getGenreAlbumsPaginated(id:String, page:Int, size:Int): GetGenreAlbumsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("music/genreql")
        val res =  apolloClient.query(
            GetGenreAlbumsPaginatedQuery
                .builder()
                .id(id)
                .page(page)
                .size(size)
                .build()
        ).await()
        return res.data
    }



    override suspend fun getGenre(id:String): GetGenreQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("music/genreql")
        val res =  apolloClient.query(
            GetGenreQuery
                .builder()
                .id(id)
                .build()
        ).await()
        return res.data
    }
    override suspend fun getUserLikedSongs(id:String): GetUserLikedSongsQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("music/userql")
        val res = apolloClient.query(
            GetUserLikedSongsQuery
                .builder()
                .id(id)
                .build()
        ).await()
        return res.data
    }

    override suspend fun getUserLikedSongsPaginated(
        id: String,
        page: Int,
        size: Int
    ): GetUserLikedSongsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("music/userql")
        val res = apolloClient.query(
            GetUserLikedSongsPaginatedQuery
                .builder()
                .id(id)
                .build()
        ).await()
        return res.data
    }



    override suspend fun  getAlbumsPaginated(page:Int, size:Int): GetAlbumsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("music/albumql")
        val res = apolloClient.query(
            GetAlbumsPaginatedQuery
                .builder()
                .page(page)
                .size(size)
                .build()
        ).await()
        return res.data
    }

    override suspend fun getUserLikedAlbumsPaginated(
        id: String,
        page: Int,
        size: Int
    ): GetUserLikedAlbumsPaginatedQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("music/userql")
        val res = apolloClient.query(
            GetUserLikedAlbumsPaginatedQuery
                .builder()
                .id(id)
                .build()
        ).await()
        return res.data
    }

    override suspend fun getAlbum(id:String): GetAlbumQuery.Data? {
        apolloClient  = apolloSetup.setUpApolloClient("music/albumql")
        val res = apolloClient.query(
            GetAlbumQuery
                .builder()
                .id(id)
                .build()
        ).await()
        return  res.data
    }


}