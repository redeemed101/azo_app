package com.fov.domain.repositories.music

import androidx.paging.PagingData
import com.fov.domain.database.models.DownloadedAlbum
import com.fov.domain.database.models.DownloadedSong
import kotlinx.coroutines.flow.Flow

interface StoredSermonRepository {
    fun getDownloadedSongs(): Flow<PagingData<DownloadedSong>>
    fun getDownloadedAlbums(): Flow<PagingData<DownloadedAlbum>>
    fun isAlbumThere(id :  String) : Flow<Boolean>
    fun isSongThere(id :  String) : Flow<Boolean>
    fun getSongPath(id: String) : Flow<String>
    fun getAlbumPath(id: String) : Flow<String>
    suspend fun deleteAllDownloadedAlbums() : Int
    suspend fun deleteAllDownloadedSongs() : Int
    suspend fun deleteDownloadedAlbum(albumId : String) : Int
    suspend fun deleteDownloadedSong(songId : String) : Int
    suspend fun saveDownloadedAlbum(album : DownloadedAlbum) : List<Long>
    suspend fun saveDownloadedSong(song : DownloadedSong) : List<Long>
}