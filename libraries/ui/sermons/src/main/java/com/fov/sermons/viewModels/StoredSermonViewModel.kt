package com.fov.sermons.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.work.WorkInfo
import com.fov.common_ui.utils.helpers.FileUtilities
import com.fov.core.di.Preferences
import com.fov.core.security.fileEncryption.FileEncryption
import com.fov.domain.database.models.DownloadedSong
import com.fov.domain.interactors.music.StoredMusicInteractor
import com.fov.sermons.events.StoredMusicEvent
import com.fov.sermons.models.Song
import com.fov.sermons.states.StoredMusicState
import com.fov.sermons.utils.helpers.Utilities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoredSermonViewModel  @Inject constructor(
    private val fileEncryption : FileEncryption,
    private val storedMusicInteractor: StoredMusicInteractor,
    private val preferences: Preferences,
    application: Application
)  : AndroidViewModel(application) {
    init{
        viewModelScope.launch {
            preferences.encryptionKey.let { key ->
                key.collectLatest { k ->
                    Log.d("secret_key", "${key}")
                    privateKey = k!!
                }

            }
        }
    }
    private val context = getApplication<Application>().applicationContext
    private var privateKey: String = ""
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
    private val _downloadStateInfo =  MutableLiveData<MutableList<Pair<String, Float?>>>(
        mutableListOf())
    private val songsDownloadQueue = MutableLiveData<List<Song>>( mutableListOf())
    val downloadStateInfo :LiveData<List<Pair<String, Float?>>> = Transformations.switchMap(
        songsDownloadQueue,
        ::downloadSong
    )

    private fun startDownload(song: Song, privateKey : String){
        if(songsDownloadQueue.value!!.firstOrNull{ p -> p.songId == song.songId } != null){
            return
        }
        this.privateKey = privateKey
        songsDownloadQueue.value = listOf(song)
        Log.d("DOWNLOADING", "${songsDownloadQueue.value!!.size}")
    }
    private fun downloadSong(songs : List<Song>) : LiveData<List<Pair<String, Float?>>>  {
        if(songs.isEmpty()){
            return liveData {
                emit(_downloadStateInfo.value!!)
            }
        }
        return Transformations.map(Utilities.downloadSong(
            context = context,
            encryptionKey = privateKey,
            song = songs.last(),
            changeDownloadData = { downloadUrl,
                                   details,
                                   destinationFilePath ->

            }
        )) { workInfo ->
            Log.d("DOWNLOADING", "going to start worker")
            when {
                workInfo.state == WorkInfo.State.RUNNING -> {
                    Log.d("DOWNLOADING_PROGRESS","${workInfo.progress.getInt("progress",1)}")

                        val progress = workInfo.progress
                        val value = progress.getInt("progress", 1)
                        _downloadStateInfo.value!!.add(
                            Pair(
                                songs.last().songId,
                                (value / 100.00).toFloat()
                            )
                        )
                        _downloadStateInfo.value!!


                }
                workInfo.state == WorkInfo.State.CANCELLED ->{
                    _downloadStateInfo.value!!.add(Pair(songs.last().songId, null))
                    _downloadStateInfo.value!!
                }
                workInfo.state == WorkInfo.State.BLOCKED->{
                    _downloadStateInfo.value!!.add(Pair(songs.last().songId, null))
                    _downloadStateInfo.value!!
                }
                workInfo.state == WorkInfo.State.FAILED->{
                    _downloadStateInfo.value!!.add(Pair(songs.last().songId, null))
                    _downloadStateInfo.value!!
                }
                workInfo.state == WorkInfo.State.SUCCEEDED -> {

                        val data = workInfo.outputData
                        val dataMap = data.keyValueMap
                        if (dataMap.containsKey("FILEPATH")) {
                            val arrPaths = dataMap["FILEPATH"] as Array<String>
                            //save to downloadedSongsDatabase
                            viewModelScope.launch {
                                storedMusicInteractor.saveDownloadedSong(
                                    DownloadedSong(
                                        songName = songs.last().songName,
                                        songPath = arrPaths[0],
                                        songId = songs.last().songId,
                                        artistName = songs.last().artistName,
                                        imagePath = arrPaths[1]

                                    )
                                )
                            }
                            _downloadStateInfo.value!!.add(Pair(songs.last().songId, null))


                        } else {
                            //show error
                            _downloadStateInfo.value!!.add(Pair(songs.last().songId, null))

                        }
                        //remove from queue
                        /*songsDownloadQueue.value!!.removeAll {
                            it.songId == songs.last().songId
                        }*/
                        _downloadStateInfo.value!!

                }
                else -> {
                    _downloadStateInfo.value!!
                }

            }
        }

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
                    val file = fileEncryption.decryptEncryptedFile(
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
                is StoredMusicEvent.DownloadSong -> {
                    startDownload(event.song,event.privateKey)
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