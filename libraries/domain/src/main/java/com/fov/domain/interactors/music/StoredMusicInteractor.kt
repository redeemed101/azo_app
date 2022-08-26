package com.fov.domain.interactors.music


import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.fov.domain.database.daos.DownloadedAlbumsDao
import com.fov.domain.database.daos.DownloadedSongsDao
import com.fov.domain.database.models.DownloadedAlbum
import com.fov.domain.database.models.DownloadedSong
import com.fov.domain.remote.apollo.music.ApolloMusicService
import com.fov.domain.repositories.music.SermonRepository
import com.fov.domain.repositories.music.StoredSermonRepository
import com.fov.domain.utils.constants.QueryConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StoredMusicInteractor constructor(
    private val storedSermonRepository: StoredSermonRepository
) {
     fun getDownloadedSongs() =
         storedSermonRepository.getDownloadedSongs()


     fun getDownloadedAlbums() =
        storedSermonRepository.getDownloadedAlbums()


    suspend fun saveDownloadedSong(song : DownloadedSong) =  withContext(Dispatchers.IO) {
          storedSermonRepository.saveDownloadedSong(song)
    }
    suspend fun saveDownloadedAlbum(album : DownloadedAlbum) =  withContext(Dispatchers.IO) {
        storedSermonRepository.saveDownloadedAlbum(album)
    }

    suspend fun deleteDownloadedSong(songId : String) = withContext(Dispatchers.IO) {
        storedSermonRepository.deleteDownloadedSong(songId)
    }

    suspend fun deleteDownloadedAlbum(albumId : String) = withContext(Dispatchers.IO) {
        storedSermonRepository.deleteDownloadedAlbum(albumId)
    }
    suspend fun deleteAllDownloadedSongs() = withContext(Dispatchers.IO) {
        storedSermonRepository.deleteAllDownloadedSongs()
    }
    suspend fun deleteAllDownloadedAlbums() = withContext(Dispatchers.IO) {
        storedSermonRepository.deleteAllDownloadedAlbums()
    }


    fun isAlbumThere(id :  String) =
        storedSermonRepository.isAlbumThere(id)


   fun isSongThere(id :  String) =
        storedSermonRepository.isSongThere(id)


}


