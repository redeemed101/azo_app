package com.fov.sermons.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val musicInteractor: MusicInteractor,
    private val storedMusicInteractor: StoredMusicInteractor
)  : ViewModel() {
    private val _uiState = MutableStateFlow(StoredMusicState())
    val uiState: StateFlow<StoredMusicState> = _uiState

    fun isAlbumDownloadedAsync(id : String) : Flow<Boolean> {

        return storedMusicInteractor.isAlbumThere(id)

    }
    fun isPlaylistDownloadedAsync(id : String) : Flow<Boolean> {
        return storedMusicInteractor.isPlaylistThere(id)

    }
    fun isSongDownloadedAsync(id : String) : Flow<Boolean> {

        return storedMusicInteractor.isSongThere(id)//CompletableDeferred(true)

    }

    fun handleMusicEvent(event: StoredMusicEvent) {
        _uiState.value = uiState.value.build {
            when (event) {
                StoredMusicEvent.LoadDownloadedSongs -> {
                    downloadedSongs =  storedMusicInteractor.getDownloadedSongs()
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
                is StoredMusicEvent.DeleteDownloadedPlaylist -> {
                    viewModelScope.launch {
                        storedMusicInteractor.deleteDownloadedPlaylist(event.playlistId)
                    }
                }
                is StoredMusicEvent.SaveDownloadedPlaylist -> {
                    viewModelScope.launch {
                        storedMusicInteractor.saveDownloadedPlaylist(event.playlist)
                    }
                }

            }
        }
    }
}