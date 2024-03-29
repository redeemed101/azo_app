package com.fov.domain.repositories.music

import androidx.paging.PagingData
import com.fov.domain.R
import com.fov.domain.database.models.DownloadedAlbum
import com.fov.domain.database.models.DownloadedSong
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.io.File

object Data {
    val SONG =  DownloadedSong(
        dbId = 1234,
        songName = "Wisdom and Creativity 1",
        songPath = "/data/data/com.fov.azo/files/rise.mp3",
        songId = "1234",
        artistName = "Apostle Ziba",
        imagePath = "/data/data/com.fov.azo/files/fov_logo-bg.png"
    )
    val SONGS =
        List(5){
            DownloadedSong(
                dbId = 1234,
                songName = "Wisdom and Creativity $it",
                songPath = "/data/data/com.fov.azo/files/rise.mp3",
                songId = "1234",
                artistName = "Apostle Ziba",
                imagePath = "/data/data/com.fov.azo/files/apostle.jpg"
            )
        }
    val ALBUMS = List(5){
        DownloadedAlbum(
            dbAlbumId = 1234,
            albumId = "1234$it",
            albumPath = "/data/data/com.fov.azo/files/Excellence",
            albumName = "Excellence and Wisdom",
            artistName = "Apostle Ziba",
            imagePath = "/data/data/com.fov.azo/files/apostle2.jpg"
        )
    }
}

class StoredSermonRepositoryMockImpl : StoredSermonRepository{
    override  fun getDownloadedSongs(): Flow<PagingData<DownloadedSong>> {
        return flowOf(PagingData.from(Data.SONGS))
    }

    override  fun getDownloadedAlbums(): Flow<PagingData<DownloadedAlbum>> {
        return flowOf(PagingData.from(Data.ALBUMS))
    }

    override fun isAlbumThere(id: String): Flow<Boolean> {
        return flowOf(true)
    }

    override  fun isSongThere(id: String): Flow<Boolean> {
        return flowOf(false)
    }

    override fun getSongPath(id: String): Flow<String> {
        return flowOf("/data/data/com.fov.azo/files/rise.mp3")
    }

    override fun getAlbumPath(id: String): Flow<String> {
        return flowOf("/data/data/com.fov.azo/files")
    }

    override fun getSong(id: String): Flow<DownloadedSong> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllDownloadedAlbums(): Int {
        return 1
    }

    override suspend fun deleteAllDownloadedSongs(): Int {
        return 5
    }

    override suspend fun deleteDownloadedAlbum(albumId: String): Int {
        return 1
    }

    override suspend fun deleteDownloadedSong(songId: String): Int {
        return 1
    }

    override suspend fun saveDownloadedAlbum(album: DownloadedAlbum): List<Long> {
        return listOf(1,1,1)
    }

    override suspend fun saveDownloadedSong(song: DownloadedSong): List<Long> {
        return listOf(1,2,2)
    }

    override suspend fun downloadFile(
        file: File,
        path: String,
        progress: (progress: Float) -> Unit,
        callback: suspend (boolean: Boolean) -> Unit,
        error : (throwable : Throwable) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}