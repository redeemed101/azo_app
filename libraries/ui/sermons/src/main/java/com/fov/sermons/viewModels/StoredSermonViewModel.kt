package com.fov.sermons.viewModels

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.*
import androidx.work.ListenableWorker
import androidx.work.WorkInfo
import androidx.work.workDataOf
import com.fov.common_ui.utils.helpers.FileUtilities
import com.fov.common_ui.workers.DownloadListener
import com.fov.common_ui.workers.DownloadTask
import com.fov.core.di.Preferences
import com.fov.core.security.fileEncryption.FileEncryption
import com.fov.domain.database.models.DownloadedSong
import com.fov.domain.interactors.music.StoredMusicInteractor
import com.fov.sermons.events.StoredMusicEvent
import com.fov.sermons.models.Song
import com.fov.sermons.states.StoredMusicState
import com.fov.sermons.utils.helpers.Utilities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.io.File
import java.util.*
import java.util.concurrent.CompletableFuture.runAsync
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
    private val songsDownloadQueue = MutableLiveData<MutableList<Song>>( mutableListOf())
    val downloadStateInfo :LiveData<List<Pair<String, Float?>>> = Transformations.map(_downloadStateInfo
    ) {
        it.toMutableList()
     } /*Transformations.switchMap(
        songsDownloadQueue,
        ::downloadSong
    )*/

    private fun startDownload(song: Song, privateKey : String){
        if(songsDownloadQueue.value!!.firstOrNull{ p -> p.songId == song.songId } != null){
            return
        }
        this.privateKey = privateKey
        if(songsDownloadQueue.value!!.isEmpty())
            songsDownloadQueue.value = mutableListOf(song)
        else{
            songsDownloadQueue.value!!.add(songsDownloadQueue.value!!.size,song)
        }
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
                    songsDownloadQueue.value!!.removeAll {
                        it.songId == songs.last().songId
                    }
                    _downloadStateInfo.value!!
                }
                workInfo.state == WorkInfo.State.BLOCKED->{
                    _downloadStateInfo.value!!.add(Pair(songs.last().songId, null))
                    _downloadStateInfo.value!!
                }
                workInfo.state == WorkInfo.State.FAILED->{
                    _downloadStateInfo.value!!.add(Pair(songs.last().songId, null))
                    songsDownloadQueue.value!!.removeAll {
                        it.songId == songs.last().songId
                    }
                    _downloadStateInfo.value!!
                }
                workInfo.state == WorkInfo.State.SUCCEEDED -> {
                       Log.i("DOWNLOAD_SUCEEDED", "Saving data coroutine")
                        val data = workInfo.outputData
                        val dataMap = data.keyValueMap
                        if (dataMap.containsKey("FILEPATH")) {
                            val arrPaths = dataMap["FILEPATH"] as Array<String>
                            //save to downloadedSongsDatabase
                            viewModelScope.launch {
                                Log.i("SAVING", "Saving data coroutine")
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
                        songsDownloadQueue.value!!.removeAll {
                            it.songId == songs.last().songId
                        }
                        _downloadStateInfo.value!!

                }
                else -> {
                    _downloadStateInfo.value!!
                }

            }
        }

    }
    private fun beginDownloadSong(song : Song) {
        val destinationPath = "$basePath/${song.songName}${FileUtilities.getFileExtension(song.path)}"
        _downloadStateInfo.value!!.add(Pair(song.songId, null))
        val job = viewModelScope.launch {
            try{
                DownloadTask(
                    context,
                    song.path,
                    destinationPath,
                    object : DownloadListener {
                        override fun onDownloadComplete(download: Boolean) {
                            Log.i("COMPLETED", "Saving data coroutine")
                            if (download) {
                                Log.i("COMPLETED", "Successful")
                                val path = "$basePath/${song.songName}${
                                    FileUtilities.getFileExtension(destinationPath)
                                }";
                                val encryptedFile =
                                    fileEncryption.encryptFile(destinationPath, path, privateKey)
                                File(destinationPath).delete()
                                if (encryptedFile != null) {
                                    Log.i("SAVING", "in not null")
                                    /*val imagePath = FileUtilities.downloadNetworkImage(
                                        song.artwork,
                                        basePath,
                                        song.songName
                                    )*/
                                    //save to downloadedSongsDatabase
                                    viewModelScope.launch {

                                        Log.d("SAVING", "Saving data coroutine")
                                        val result = storedMusicInteractor.saveDownloadedSong(
                                            DownloadedSong(
                                                songName = song.songName,
                                                songPath = encryptedFile.absolutePath,
                                                songId = song.songId,
                                                artistName = song.artistName,
                                                imagePath = "https://picsum.photos/200"
                                            )
                                        )
                                        Log.d("SAVING", "${result.size}")
                                    }
                                }

                                val new = _downloadStateInfo.value!!.filter { p -> p.first != song.songId }
                                    .toMutableList()
                                _downloadStateInfo.postValue(new)
                            } else {
                                Log.i("COMPLETED", "failed")
                                //also set song downloaded
                                val new = _downloadStateInfo.value!!.filter { p -> p.first != song.songId }
                                    .toMutableList()
                                new.add(Pair(song.songId, null))
                                _downloadStateInfo.postValue(new)

                            }
                            //remove from queue
                            songsDownloadQueue.value!!.removeAll {
                                it.songId == song.songId
                            }

                        }

                        override fun downloadProgress(status: Double) {
                            Log.d("DOWNLOADING_PROGRESS", "$status")

                            val new = _downloadStateInfo.value!!.filter { p -> p.first != song.songId }
                                .toMutableList()
                            new.add(Pair(song.songId, status.toFloat()))
                            _downloadStateInfo.postValue(new)
                        }

                        override fun errorOccurred(throwable: Throwable) {
                            songsDownloadQueue.value!!.removeAll {
                                it.songId == song.songId
                            }
                            val new = _downloadStateInfo.value!!.filter { p -> p.first != song.songId }
                                .toMutableList()
                            new.add(Pair(song.songId, null))
                            _downloadStateInfo.postValue(new)
                        }

                    }
                ).downloadFile()
        }
        finally {
            val new = _downloadStateInfo.value!!.filter { p -> p.first != song.songId }
                .toMutableList()
            new.add(Pair(song.songId, null))
            _downloadStateInfo.postValue(new)
            songsDownloadQueue.value!!.removeAll {
                it.songId == song.songId
            }

         }
       }

    }
    private fun beginDownload(song : Song)  = run {
        val url = song.path
        var fileName = song.songName
        fileName = "${basePath}/${fileName}.mp3"
        Log.d("DOWNLOADING", fileName)
        val file: File = File(fileName)
        val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(url))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE) // Visibility of the download Notification
            .setDestinationUri(Uri.fromFile(file)) // Uri of the destination file
            .setTitle(fileName) // Title of the Download Notification
            .setDescription("Downloading") // Description of the Download Notification
            .setRequiresCharging(false) // Set if charging is required to begin the download
            .setAllowedOverMetered(true) // Set if download is allowed on Mobile network
            .setAllowedOverRoaming(true) // Set if download is allowed on roaming network
        val downloadManager: DownloadManager =
            getApplication<Application>().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadID = downloadManager.enqueue(request) // enqueue puts the download request in the queue.

        // using query method
        var finishDownload = false
        var progress: Float
        while (!finishDownload) {
            val cursor: Cursor = downloadManager.query(DownloadManager.Query().setFilterById(downloadID))
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                Log.d("DOWNLOADING", "${index}")
                val status: Int = cursor.getInt(index)
                when (status) {
                    DownloadManager.STATUS_FAILED -> {
                        finishDownload = true
                        _downloadStateInfo.value!!.add(Pair(song.songId, null))
                        songsDownloadQueue.value!!.removeAll {
                            it.songId == song.songId
                        }
                        liveData {
                            emit(_downloadStateInfo.value!!.toList())
                        }
                    }
                    DownloadManager.STATUS_PAUSED -> {}
                    DownloadManager.STATUS_PENDING -> {}
                    DownloadManager.STATUS_RUNNING -> {
                        val t = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
                        val total: Long = cursor.getLong(t)
                        if (total >= 0) {
                            val tx = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                            val downloaded: Long =  cursor.getLong(tx)
                            progress = (downloaded * 100L / total).toFloat()
                            _downloadStateInfo.value!!.add(Pair(song.songId, progress))
                            liveData {
                                emit(_downloadStateInfo.value!!.toList())
                            }
                        }
                    }
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        progress = 100f
                        _downloadStateInfo.value!!.add(Pair(song.songId, progress))
                        /*val basePath = "${com.fov.common_ui.utils.helpers.Utilities
                            .getDataDirectory(
                                context
                            ).absolutePath}"
                        //save to downloadedSongsDatabase
                        viewModelScope.launch {
                            storedMusicInteractor.saveDownloadedSong(
                                DownloadedSong(
                                    songName = song.songName,
                                    songPath = arrPaths[0],
                                    songId = song.songId,
                                    artistName = song.artistName,
                                    imagePath = arrPaths[1]

                                )
                            )
                        }*/
                        file.delete()
                        songsDownloadQueue.value!!.removeAll {
                            it.songId == song.songId
                        }
                        liveData {
                            emit(_downloadStateInfo.value!!.toList())
                        }
                        finishDownload = true

                    }
                }
            }
            cursor.close()
        }
        _downloadStateInfo.value!!.add(Pair(song.songId, null))
        liveData {
            emit(_downloadStateInfo.value!!.toList())
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
                    beginDownloadSong(event.song)
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