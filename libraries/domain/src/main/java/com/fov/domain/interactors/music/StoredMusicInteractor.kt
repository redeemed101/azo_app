package com.fov.domain.interactors.music


import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.fov.domain.database.daos.DownloadedAlbumsDao
import com.fov.domain.database.daos.DownloadedSongsDao
import com.fov.domain.database.models.DownloadedAlbum
import com.fov.domain.database.models.DownloadedSong
import com.fov.domain.remote.apollo.music.ApolloMusicService
import com.fov.domain.repositories.music.SermonRepository
import com.fov.domain.utils.constants.QueryConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StoredMusicInteractor constructor(
    private val downloadedSongsDao: DownloadedSongsDao,
    private val downloadedAlbumsDao: DownloadedAlbumsDao,
    private val sermonRepository: SermonRepository,
    private val sermonGraphQLService: ApolloMusicService
) {
     fun getDownloadedSongs() =
            Pager(
                PagingConfig(pageSize = QueryConstants.NUM_ROWS),
                null,
                downloadedSongsDao.getDownloadedSongsPaginated()
                    .asPagingSourceFactory(
                    )
            ).flow

    fun getDownloadedAlbums() =
        Pager(
            PagingConfig(pageSize = QueryConstants.NUM_ROWS),
            null,
            downloadedAlbumsDao.getDownloadedAlbumsPaginated().asPagingSourceFactory(

            )
        ).flow

    suspend fun saveDownloadedSong(song : DownloadedSong) =  withContext(Dispatchers.IO) {
       if(song.dbId == null)
         downloadedSongsDao.insertAll(song)
        else
            downloadedSongsDao.update(song)
    }
    suspend fun saveDownloadedAlbum(album : DownloadedAlbum) =  withContext(Dispatchers.IO) {
        if(album.dbId == null)
            downloadedAlbumsDao.insertAll(album)
        else
            downloadedAlbumsDao.update(album)
    }

    suspend fun deleteDownloadedSong(songId : String) = withContext(Dispatchers.IO) {
        downloadedSongsDao.deleteSong(songId)
    }

    suspend fun deleteDownloadedAlbum(albumId : String) = withContext(Dispatchers.IO) {
        downloadedAlbumsDao.deleteAlbum(albumId)
    }
    suspend fun deleteAllDownloadedSongs() = withContext(Dispatchers.IO) {
        downloadedSongsDao.deleteAll()
    }
    suspend fun deleteAllDownloadedAlbums() = withContext(Dispatchers.IO) {
        downloadedAlbumsDao.deleteAll()
    }


    fun isAlbumThere(id :  String) =
        downloadedAlbumsDao.doesAlbumExist(id)

     fun isSongThere(id :  String)
        =  downloadedSongsDao.doesSongExist(id)

}


