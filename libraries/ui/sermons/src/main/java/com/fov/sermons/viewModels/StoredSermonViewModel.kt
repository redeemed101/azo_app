package com.fov.sermons.viewModels

import android.app.Application
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
import kotlinx.coroutines.flow.*
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
                              /*storedMusicInteractor.downloadFile(
                                  File(imagePath),
                                  album.artwork,
                                  { progress ->

                                  },
                                  { done ->
                                      if(!done)
                                          imagePath = "https://picsum.photos/200"
                                  },
                                  {
                                      imagePath = "https://picsum.photos/200"
                                  }
                              )*/
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
                      _uiState.value.build {
                          error = throwable.message
                      }
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

                            _uiState.value.build {
                                error = throwable.message
                            }
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
                    if(!File(destinationFilePath).exists()) {
                        val file = fileEncryption.decryptEncryptedFile(
                            event.song.songPath,
                            destinationFilePath,
                            event.secretKey
                        )
                    }
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
                        val s = storedMusicInteractor.getSong(event.songId).first()
                        if(s != null){
                            val imageFile = File(s.imagePath)
                            if(imageFile.exists()) {
                               imageFile.delete()

                            }
                            val songFile = File(s.songPath)
                            if (songFile.exists()){
                                songFile.delete()
                            }
                        }
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
                StoredMusicEvent.ClearError -> {
                    error = null
                }


                else -> {}
            }
        }
    }
}