package com.fov.sermons.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fov.common_ui.utils.helpers.FileUtilities
import com.fov.core.security.fileEncryption.FileEncryption
import com.fov.domain.interactors.music.MusicInteractor
import com.fov.domain.interactors.music.StoredMusicInteractor
import com.fov.sermons.events.StoredMusicEvent
import com.fov.sermons.states.StoredMusicState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoredSermonViewModel  @Inject constructor(
    private val fileEncryption : FileEncryption,
    private val musicInteractor: MusicInteractor,
    private val storedMusicInteractor: StoredMusicInteractor,
    application: Application
)  : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    var basePath = "${
        com.fov.common_ui.utils.helpers.Utilities
            .getCacheDirectory(
                context
            ).absolutePath}"
    private val _uiState = MutableStateFlow(StoredMusicState())
    val uiState: StateFlow<StoredMusicState> = _uiState

    fun isAlbumDownloadedAsync(id : String) : Flow<Boolean> {

        return storedMusicInteractor.isAlbumThere(id)

    }

    fun getSongPath(id: String) : Flow<String> = storedMusicInteractor.getSongPath(id)
    fun getAlbumPath(id: String) : Flow<String> = storedMusicInteractor.getAlbumPath(id)

    fun isSongDownloadedAsync(id : String) : Flow<Boolean> {

        return storedMusicInteractor.isSongThere(id)

    }

    fun handleMusicEvent(event: StoredMusicEvent) {
        _uiState.value = uiState.value.build {
            when (event) {
                is StoredMusicEvent.UpdateSongDownloadProgress ->{
                    var v = songDownloadProgress.getOrDefault(event.songId,null)
                    if(v != null){
                        songDownloadProgress[event.songId] = event.progress
                    }
                    else{
                        songDownloadProgress[event.songId] = event.progress
                    }

                }
                is StoredMusicEvent.DecryptSong -> {
                    val destinationFilePath = "$basePath/${event.song.songName}" +
                            "${FileUtilities.getFileExtension(event.song.songPath)}"
                    fileEncryption.decryptEncryptedFile(
                        event.song.songPath,
                        destinationFilePath,
                        event.secretKey
                    )
                }
                StoredMusicEvent.LoadDownloadedSongs -> {

                        downloadedSongs = storedMusicInteractor.getDownloadedSongs()

                }
                StoredMusicEvent.LoadDownloadedAlbums -> {

                        downloadedAlbums = storedMusicInteractor.getDownloadedAlbums()

                }
                is StoredMusicEvent.SaveDownloadedSong -> {
                    viewModelScope.launch {
                        storedMusicInteractor.saveDownloadedSong(event.song)
                    }
                }
                is StoredMusicEvent.DeleteDownloadedSong -> {
                    viewModelScope.launch {
                        storedMusicInteractor.deleteDownloadedSong(event.songId)
                    }
                }
                is StoredMusicEvent.SaveDownloadedAlbum -> {
                    viewModelScope.launch {
                        storedMusicInteractor.saveDownloadedAlbum(event.album)
                    }
                }
                is StoredMusicEvent.DeleteDownloadedAlbum -> {
                    viewModelScope.launch {
                        storedMusicInteractor.deleteDownloadedAlbum(event.albumId)
                    }
                }


                else -> {}
            }
        }
    }
}