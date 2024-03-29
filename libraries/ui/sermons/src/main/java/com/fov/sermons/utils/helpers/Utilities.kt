package com.fov.sermons.utils.helpers

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import com.fov.common_ui.utils.helpers.FileUtilities
import com.fov.sermons.R
import com.fov.sermons.models.Album
import com.fov.sermons.models.Song
import java.io.File

val GENRE_IMAGES = listOf(
      R.drawable.genre1,
      R.drawable.genre2,
      R.drawable.genre3,
    R.drawable.genre4,
    R.drawable.genre5,
    R.drawable.genre6,
    R.drawable.genre7,
    R.drawable.genre8,
    R.drawable.genre9,
    R.drawable.genre10,
    R.drawable.genre11,
    R.drawable.genre12,
    R.drawable.genre13,
    R.drawable.genre14,
    R.drawable.genre0,
)

class Utilities {
    companion object{

        fun downloadAlbum(context: Context,
                          lifecycleOwner: LifecycleOwner,
                          album : Album,
                          changeDownloadData: (downloadUrl : String, details : String, destinationFilePath : String) -> Unit,
                          saveSong : (song: Song, songPath : String, imagePath : String) -> Unit,
                          saveAlbum : (albumPath : String, imagePath : String) -> Unit
        ) : List<LiveData<WorkInfo>>{

           val albumPath = "${
               com.fov.common_ui.utils.helpers.Utilities
                   .getDataDirectory(
                       context
                   ).absolutePath}/${album.albumName}"
            val albumDir = File(albumPath)
            var results = mutableListOf<LiveData<WorkInfo>>()
            album.songs.forEach { song ->
                    val res = downloadSong(
                        context = context,
                        song = song,
                        albumPath = albumDir.absolutePath,
                        changeDownloadData = changeDownloadData,

                    )
                results.add(res)
            }
            val albumImage = FileUtilities.downloadNetworkImage(album.artwork,albumDir.absolutePath,album.albumName)
            //save album
            saveAlbum(albumDir.absolutePath,albumImage)
            return results
        }
        fun unDownloadSong(songPath: String,
                           onDelete : () -> Unit) : Boolean{
            var success  = false
            val file = File(songPath)
            if(file.exists()) {
                val deleted = file.delete()
                if(deleted) {
                    onDelete()
                }
                success = deleted
            }
            return success
        }

        fun unDownloadAlbum(albumPath: String,
                           onDelete: () -> Unit) : Boolean{
            var success  = false
            val file = File(albumPath)
            if(file.exists()) {
                val deleted = file.deleteRecursively()
                if(deleted){
                    onDelete()
                }
                success = deleted
            }
            return success
        }
        fun downloadSong(context : Context,
                         song: Song,
                         encryptionKey: String = "",
                         albumPath : String? = null,
                         changeDownloadData: (downloadUrl : String, details : String, destinationFilePath : String) -> Unit,

                        ) : LiveData<WorkInfo> {

            val downloadUrl = song.path
            val imageUrl = song.artwork
            val details = song.songName
            var basePath = "${
                com.fov.common_ui.utils.helpers.Utilities
                    .getCacheDirectory(
                        context
                    ).absolutePath}"
            if(albumPath != null){
                basePath = albumPath
            }
            val destinationFilePath =  "$basePath/${song.songName}${FileUtilities.getFileExtension(song.path)}"
            Log.d("DOWNLOADING", destinationFilePath)
            changeDownloadData(downloadUrl, details, destinationFilePath)

            return FileUtilities.downloadSongFile(
                url = downloadUrl,
                id = song.songId,
                imageUrl = imageUrl,
                encryptionKey = encryptionKey,
                destinationFile = destinationFilePath,
                fileDetails = details,
                applicationContext = context
            )


        }


    }
}