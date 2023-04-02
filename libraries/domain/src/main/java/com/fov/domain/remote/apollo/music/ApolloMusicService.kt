package com.fov.domain.remote.apollo.music


import com.fov.domain.music.*
import com.fov.domain.albums.GetAlbumQuery
import com.fov.domain.albums.GetAlbumsPaginatedQuery
import com.fov.domain.genres.*
import com.fov.domain.songs.GetSongsByYearPaginatedQuery
import com.fov.domain.songs.GetSongsPaginatedQuery
import com.fov.domain.songs.GetTrendingSongsPaginatedQuery

interface ApolloMusicService {

    suspend fun  getGenres(token: String): GetGenresQuery.Data?
    suspend fun  getGenreSongsPaginated(token: String,id:String,page:Int,size:Int): GetGenreSongsPaginatedQuery.Data?
    suspend fun  getGenreAlbumsPaginated(token: String,id:String,page:Int,size:Int): GetGenreAlbumsPaginatedQuery.Data?
    suspend fun getGenre(token: String,id:String): GetGenreQuery.Data?
    suspend fun getUserLikedSongs(token: String,id:String): GetUserLikedSongsQuery.Data?
    suspend fun getSongsPaginated(token: String,page:Int,size:Int) : GetSongsPaginatedQuery.Data?
    suspend fun getTrendingSongsPaginated(token: String,page:Int,size:Int) : GetTrendingSongsPaginatedQuery.Data?
    suspend fun getSongsByYearPaginated(token: String,year:Int,page:Int,size:Int) : GetSongsByYearPaginatedQuery.Data?
    suspend fun getUserLikedSongsPaginated(token: String,id:String,page: Int,size: Int): GetUserLikedSongsPaginatedQuery.Data?
    suspend fun getAlbumsPaginated(token: String,page:Int,size:Int): GetAlbumsPaginatedQuery.Data?
    suspend fun getUserLikedAlbumsPaginated(token: String,id:String,page: Int,size: Int): GetUserLikedAlbumsPaginatedQuery.Data?
    suspend fun getAlbum(token: String,id:String): GetAlbumQuery.Data?
}