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
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import java.io.File

class StoredSermonRepositoryImpl constructor(
    private val downloadedSongsDao: DownloadedSongsDao,
    private val downloadedAlbumsDao: DownloadedAlbumsDao,
    private val client: HttpClient
)
    : StoredSermonRepository {
    override fun getDownloadedSongs(): Flow<PagingData<DownloadedSong>>
    =  Pager(
        PagingConfig(pageSize = QueryConstants.NUM_ROWS),
        null,
        downloadedSongsDao.getDownloadedSongsPaginated()
            .asPagingSourceFactory(
            )
    ).flow

    override suspend fun downloadFile(destinationFile: File, url: String,
                                      progress: (progress : Float) -> Unit,
                                      callback: suspend (boolean: Boolean) -> Unit,
                                      error : (throwable : Throwable) -> Unit
    ) {
        try{
        val response = client.get {
            url(url)
        }.call.response
        val data = ByteArray(response.contentLength()!!.toInt())
        var offset = 0
        do {
            val currentRead = response.bodyAsChannel().readAvailable(data, offset, data.size)
            offset += currentRead
            val progress = (offset.toFloat() * 100f / data.size.toFloat())
            Log.d("DOWNLOAD_PROGRESS","$progress")
            progress(progress)
        } while (currentRead > 0)
        //response.close()
        if (!response.status.isSuccess()) {
            callback(false)
        }
        destinationFile.writeBytes(data)
        callback(true)
        }
        catch (throwable: Throwable) {
            Log.e("Download_failure", "Error downloading file ${throwable.message}")
            throwable.printStackTrace()
            error(throwable)
        }
    }

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
    override fun getSong(id: String): Flow<DownloadedSong> = downloadedSongsDao.getSong(id)

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