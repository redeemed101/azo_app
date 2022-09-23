package com.fov.domain.repositories.music

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fov.domain.database.daos.DownloadedAlbumsDao
import com.fov.domain.database.daos.DownloadedSongsDao
import com.fov.domain.database.models.DownloadedAlbum
import com.fov.domain.database.models.DownloadedSong
import com.fov.domain.utils.constants.QueryConstants
import kotlinx.coroutines.flow.Flow

class StoredSermonRepositoryImpl constructor(
    private val downloadedSongsDao: DownloadedSongsDao,
    private val downloadedAlbumsDao: DownloadedAlbumsDao)
    : StoredSermonRepository {
    override fun getDownloadedSongs(): Flow<PagingData<DownloadedSong>>
    =  Pager(
        PagingConfig(pageSize = QueryConstants.NUM_ROWS),
        null,
        downloadedSongsDao.getDownloadedSongsPaginated()
            .asPagingSourceFactory(
            )
    ).flow

    override  fun getDownloadedAlbums(): Flow<PagingData<DownloadedAlbum>> =  Pager(
        PagingConfig(pageSize = QueryConstants.NUM_ROWS),
        null,
        downloadedAlbumsDao.getDownloadedAlbumsPaginated().asPagingSourceFactory(

        )
    ).flow

    override fun isAlbumThere(id: String): Flow<Boolean> =
        downloadedAlbumsDao.doesAlbumExist(id)

    override fun  getSongPath(id: String) = downloadedSongsDao.getSongPath(id)

    override fun  getAlbumPath(id: String) = downloadedAlbumsDao.getAlbumPath(id)

    override fun isSongThere(id: String): Flow<Boolean> =  downloadedSongsDao.doesSongExist(id)

    override suspend fun deleteAllDownloadedAlbums() : Int=  downloadedAlbumsDao.deleteAll()

    override suspend fun deleteAllDownloadedSongs() : Int =  downloadedSongsDao.deleteAll()

    override suspend fun deleteDownloadedAlbum(albumId: String) : Int =  downloadedAlbumsDao.deleteAlbum(albumId)

    override suspend fun deleteDownloadedSong(songId: String) : Int =  downloadedSongsDao.deleteSong(songId)

    override suspend fun saveDownloadedAlbum(album: DownloadedAlbum) : List<Long> {

            return downloadedAlbumsDao.insertAll(album)

    }

    override suspend fun saveDownloadedSong(song: DownloadedSong) : List<Long> {
        return downloadedSongsDao.insertAll(song)
    }
}