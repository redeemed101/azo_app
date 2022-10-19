package com.fov.domain.remote.apollo.music


import com.fov.domain.music.*
import com.fov.domain.albums.GetAlbumQuery
import com.fov.domain.albums.GetAlbumsPaginatedQuery
import com.fov.domain.genres.*
import com.fov.domain.songs.GetSongsPaginatedQuery
import com.fov.domain.songs.GetTrendingSongsPaginatedQuery

interface ApolloMusicService {

    suspend fun  getGenres(): GetGenresQuery.Data?
    suspend fun  getGenreSongsPaginated(id:String,page:Int,size:Int): GetGenreSongsPaginatedQuery.Data?
    suspend fun  getGenreAlbumsPaginated(id:String,page:Int,size:Int): GetGenreAlbumsPaginatedQuery.Data?
    suspend fun getGenre(id:String): GetGenreQuery.Data?
    suspend fun getUserLikedSongs(id:String): GetUserLikedSongsQuery.Data?
    suspend fun getSongsPaginated(page:Int,size:Int) : GetSongsPaginatedQuery.Data?
    suspend fun getTrendingSongsPaginated(page:Int,size:Int) : GetTrendingSongsPaginatedQuery.Data?
    suspend fun getUserLikedSongsPaginated(id:String,page: Int,size: Int): GetUserLikedSongsPaginatedQuery.Data?
    suspend fun getAlbumsPaginated(page:Int,size:Int): GetAlbumsPaginatedQuery.Data?
    suspend fun getUserLikedAlbumsPaginated(id:String,page: Int,size: Int): GetUserLikedAlbumsPaginatedQuery.Data?
    suspend fun getAlbum(id:String): GetAlbumQuery.Data?
}