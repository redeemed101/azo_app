package com.fov.sermons.viewModels

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.fov.common_ui.utils.helpers.FileUtilities
import com.fov.common_ui.workers.*
import com.fov.core.di.Preferences
import com.fov.core.security.fileEncryption.FileEncryption
import com.fov.domain.database.models.DownloadedAlbum
import com.fov.domain.database.models.DownloadedSong
import com.fov.domain.interactors.music.StoredMusicInteractor
import com.fov.sermons.events.StoredMusicEvent
import com.fov.sermons.models.Album
import com.fov.sermons.models.Song
import com.fov.sermons.states.StoredMusicState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
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
    var baseCachePath = com.fov.common_ui.utils.helpers.Utilities
            .getCacheDirectory(
                context
            ).absolutePath

    var baseDataPath = com.fov.common_ui.utils.helpers.Utilities
        .getOutputDirectory(
            context
        ).absolutePath

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
     }

    private val _albumDownloadStateInfo = MutableLiveData<MutableList<Pair<String, Float?>>>(
        mutableListOf()
    )
    val albumDownloadStateInfo : LiveData<List<Pair<String, Float?>>> = Transformations.map(_albumDownloadStateInfo
    ) {
        it.toMutableList()
    }

    private val _successfulDownloads =  MutableLiveData<MutableList<Pair<String, Boolean>>>(
        mutableListOf())

    private fun downloadAlbum(album: Album,privateKey : String){
        _albumDownloadStateInfo.value!!.add(Pair(album.albumId, null))
        _albumDownloadStateInfo.value = _albumDownloadStateInfo.value

        val albumPermanentPath = "${baseDataPath}/${album.albumName}"
        Log.d("ALBUM_DOWNLOAD", "Within $albumPermanentPath")
        val albumTempPath = "${baseCachePath}/${album.albumName}"
        val albumDir = File(albumPermanentPath).apply {
            mkdirs()
        }
        val albumTempDir = File(albumTempPath)
        val multipleDownloads : MutableList<MultipleDownload> = mutableListOf()
        album.songs.forEach { song ->
            multipleDownloads.add(
                MultipleDownload(
                    identifier= song.songId,
                    url = song.path,
                    destinationPath = "$albumTempPath/${song.songName}${FileUtilities.getFileExtension(song.path)}"
                )
            )
        }
        var downloadedSongPaths : MutableList<String> = mutableListOf()
        var encryptedSongPaths : MutableList<String> = mutableListOf()
        val downloadedSongs : MutableList<Song> = mutableListOf()
        viewModelScope.launch {
            MultipleDownloadsTask(
              downloadPaths = multipleDownloads,
              listener = object : MultipleDownloadsListener{
                  override fun onDownloadComplete(download: Boolean) {
                      if(download){
                          //encrypt
                          for(i in downloadedSongPaths.indices){
                              val encryptionDestinationPath = "$albumPermanentPath/${downloadedSongs[i].songName}${
                                  FileUtilities.getFileExtension(downloadedSongPaths[i])
                              }";
                              Log.d("ENCRYPTION_PATH", "${downloadedSongPaths[i]} -> $encryptionDestinationPath")
                              val encryptedFile =    fileEncryption.encryptFile(downloadedSongPaths[i], encryptionDestinationPath, privateKey)
                              File(downloadedSongPaths[i]).delete()
                              if(i == downloadedSongPaths.size - 1){
                                  albumTempDir.deleteRecursively()
                              }
                              if (encryptedFile != null) {
                                  encryptedSongPaths.add(encryptionDestinationPath)
                              }
                              else{
                                   //delete the encrypted so far
                                  albumDir.deleteRecursively()
                                  //delete all
                                  albumTempDir.deleteRecursively()

                                  break

                              }
                          }


                          viewModelScope.launch {
                              //save album file
                              var imagePath  = "$albumPermanentPath/${album.albumName}.jpg"
                              DownloadTask(
                                  context,
                                  album.artwork,
                                  imagePath,
                                  object: DownloadListener{
                                      override fun onDownloadComplete(download: Boolean) {
                                          //use default image
                                          if (!download)
                                             imagePath = "https://picsum.photos/200"
                                      }
                                      override fun downloadProgress(status: Double) {

                                      }
                                      override fun errorOccurred(throwable: Throwable) {
                                          //use default image
                                          imagePath = "https://picsum.photos/200"
                                      }

                                  }
                              ).downloadFile()
                              //save DownloadedAlbum
                              val result = storedMusicInteractor.saveDownloadedAlbum(
                                  DownloadedAlbum(
                                      albumName = album.albumName,
                                      albumPath = albumPermanentPath,
                                      albumId = album.albumId,
                                      artistName = album.artistName,
                                      imagePath = imagePath
                                  )
                              )
                              if(result.isEmpty()){
                                  //not saved...what to do
                              }
                              else {
                                  //save each DownloadSong including downloading image (use album image)
                                  for(i in encryptedSongPaths.indices) {
                                      val result = storedMusicInteractor.saveDownloadedSong(
                                          DownloadedSong(
                                              songName = downloadedSongs[i].songName,
                                              songPath = encryptedSongPaths[i],
                                              parentAlbumId = result.first(),
                                              songId = downloadedSongs[i].songId,
                                              artistName = album.artistName,
                                              imagePath = imagePath
                                          )
                                      )
                                  }
                              }
                              val new = _albumDownloadStateInfo.value!!.filter { p -> p.first != album.albumId }
                                  .toMutableList()
                              _albumDownloadStateInfo.postValue(new)


                          }

                      }
                      else{
                          //delete all downloaded
                          albumTempDir.deleteRecursively()
                          val new = _albumDownloadStateInfo.value!!.filter { p -> p.first != album.albumId }
                              .toMutableList()
                          _albumDownloadStateInfo.postValue(new)
                      }

                  }

                  override fun onOneDownloadComplete(downloadedPath: String) {
                      Log.d("DOWNLOAD_PATH", downloadedPath)
                      downloadedSongPaths.add(downloadedPath)
                      val m = multipleDownloads.find { m -> m.destinationPath == downloadedPath }
                      if (m != null){
                          Log.d("DOWNLOAD_PATH__", m.url)
                          val s = album.songs.find{ s -> s.songId == m.identifier}
                          if (s != null) {
                              downloadedSongs.add(s)
                          }
                      }

                  }

                  override fun downloadProgress(status: Double) {
                      //progress for a song

                  }

                  override fun downloadTotalProgress(status: Double) {
                      //progress number of finished over total to download
                      val new = _albumDownloadStateInfo.value!!.filter { p -> p.first != album.albumId }
                          .toMutableList()
                      new.add(Pair(album.albumId, status.toFloat()))
                      _albumDownloadStateInfo.postValue(new)
                  }

                  override fun errorOccurred(throwable: Throwable) {
                      //delete all downloaded
                      albumTempDir.deleteRecursively()
                      val new = _albumDownloadStateInfo.value!!.filter { p -> p.first != album.albumId }
                          .toMutableList()
                      _albumDownloadStateInfo.postValue(new)
                  }

              }
            ).downloadFile()
        }

    }

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

    }

    private fun beginDownloadSong(song : Song) {
        val tempDestinationPath = "$baseCachePath/${song.songName}${FileUtilities.getFileExtension(song.path)}"
        _downloadStateInfo.value!!.add(Pair(song.songId, null))
        val job = viewModelScope.launch {
            try{
                DownloadTask(
                    context,
                    song.path,
                    tempDestinationPath,
                    object : DownloadListener {
                        override fun onDownloadComplete(download: Boolean) {

                            if (download) {
                                val encryptionDestinationPath = "$baseDataPath/${song.songName}${
                                    FileUtilities.getFileExtension(tempDestinationPath)
                                }";
                                val encryptedFile =
                                    fileEncryption.encryptFile(tempDestinationPath, encryptionDestinationPath, privateKey)
                                File(tempDestinationPath).delete()
                                if (encryptedFile != null) {
                                    Log.i("SAVING", "in not null")

                                    //save to downloadedSongsDatabase
                                    viewModelScope.launch {
                                        var imagePath : String = "$baseDataPath/${song.songName}.jpg"
                                         DownloadTask(
                                             context,
                                             song.artwork,
                                             imagePath,
                                             object: DownloadListener{
                                                 override fun onDownloadComplete(download: Boolean) {

                                                 }
                                                 override fun downloadProgress(status: Double) {

                                                 }
                                                 override fun errorOccurred(throwable: Throwable) {

                                                 }

                                             }
                                         )
                                             .downloadFile()

                                        Log.d("SAVING", "Saving data coroutine")
                                        val result = storedMusicInteractor.saveDownloadedSong(
                                            DownloadedSong(
                                                songName = song.songName,
                                                songPath = encryptedFile.absolutePath,
                                                songId = song.songId,
                                                artistName = song.artistName,
                                                imagePath = imagePath ?: "https://picsum.photos/200"
                                            )
                                        )
                                        Log.d("SAVING", "${result.size}")
                                        Log.d("DOWNLOAD_DONE", "remove from state")
                                         _successfulDownloads.value!!.add(
                                            Pair(song.songId,true)
                                        )
                                        _successfulDownloads.postValue(_successfulDownloads.value)

                                        val new = _downloadStateInfo.value!!.filter { p -> p.first != song.songId }
                                            .toMutableList()
                                        _downloadStateInfo.postValue(new)
                                    }
                                }

                            } else {
                                Log.i("COMPLETED", "failed")

                                val new = _downloadStateInfo.value!!.filter { p -> p.first != song.songId }
                                    .toMutableList()
                                _downloadStateInfo.postValue(new)

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

                            val new = _downloadStateInfo.value!!.filter { p -> p.first != song.songId }
                                .toMutableList()
                            _downloadStateInfo.postValue(new)
                        }

                    }
                ).downloadFile()
        }
        finally {

            songsDownloadQueue.value!!.removeAll {
                it.songId == song.songId
            }

         }
       }

    }
    private fun beginDownload(song : Song)  = run {
        val url = song.path
        var fileName = song.songName
        fileName = "${baseCachePath}/${fileName}.mp3"
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

    fun isSongDownloadedAsync(id : String) : Flow<Boolean> =  storedMusicInteractor.isSongThere(id)


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
                    val destinationFilePath = "$baseCachePath/${event.song.songName}" +
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
                is StoredMusicEvent.DownloadAlbum -> {
                    downloadAlbum(event.album, event.privateKey)
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