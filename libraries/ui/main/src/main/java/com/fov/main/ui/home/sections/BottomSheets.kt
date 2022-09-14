package com.fov.main.ui.home.sections

import android.content.Context
import android.util.Log
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.LifecycleOwner
import com.fov.authentication.events.UsersEvent
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.models.DownloadData
import com.fov.common_ui.utils.BottomSheetOption
import com.fov.domain.database.models.DownloadedAlbum
import com.fov.domain.database.models.DownloadedSong
import com.fov.main.ui.general.*
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.events.StoredMusicEvent
import com.fov.sermons.models.Album
import com.fov.sermons.utils.helpers.Utilities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.fov.sermons.R
import com.fov.sermons.models.Song

@ExperimentalMaterialApi
fun userBottomSheet(
    events : (CommonEvent) -> Unit,
    userEvents : (UsersEvent) -> Unit,
    scope: CoroutineScope,
    bottomSheetScaffoldState : BottomSheetScaffoldState,
    context: Context,
    lifecycleOwner: LifecycleOwner,
    userName : String = "",
){
    bottomSheetAction(
        scope = scope,
        bottomSheetScaffoldState,
        listOf(
            BottomSheetOption(null, "Share Profile") {
                shareProfile(share = "", context)
                scope.launch {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            },
            BottomSheetOption(null, "Block") {
                copyToClipboard("", context)
                scope.launch {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            },

        ),
        events
    )
}

@ExperimentalMaterialApi
fun ownerUserBottomSheet(
    events : (CommonEvent) -> Unit,
    userEvents : (UsersEvent) -> Unit,
    scope: CoroutineScope,
    bottomSheetScaffoldState : BottomSheetScaffoldState,
    context: Context,
    lifecycleOwner: LifecycleOwner,
    userName : String = ""
){
    bottomSheetAction(
        scope = scope,
        bottomSheetScaffoldState,
        listOf(
            BottomSheetOption(null, "Share Profile") {
                shareProfile(share = "", context)
            },
            BottomSheetOption(null, "Copy Profile URL") {
                copyToClipboard("", context)
            },


        ),
        events
    )
}



@OptIn(ExperimentalMaterialApi::class)
fun albumBottomSheet(
    album : Album,
    events : (CommonEvent) -> Unit,
    musicEvents : (MusicEvent) -> Unit,
    storedMusicEvents : (StoredMusicEvent) -> Unit,
    scope: CoroutineScope,
    bottomSheetScaffoldState : BottomSheetScaffoldState,
    context: Context,
    lifecycleOwner: LifecycleOwner,
    isDownloaded : Boolean,
    isLiked : Boolean){

    bottomSheetAction(
        scope = scope,
        bottomSheetScaffoldState,
        listOf(
            BottomSheetOption(
                R.drawable.ic_arrow_down_circle ,
                if(isDownloaded) "Downloaded" else "Download") {
                if(!isDownloaded) {
                        Utilities.downloadAlbum(
                            context = context,
                            lifecycleOwner = lifecycleOwner,
                            album = album,
                            changeDownloadData = { downloadUrl, details, destinationFilePath ->
                                events(
                                    CommonEvent.ChangeDownloadData(
                                        DownloadData(
                                            downloadUrl = downloadUrl,
                                            details = details,
                                            destinationFilePath = destinationFilePath
                                        )
                                    )
                                )
                            },
                            saveSong = { song, songPath, imagePath ->
                                storedMusicEvents(
                                    StoredMusicEvent.SaveDownloadedSong(
                                        DownloadedSong(
                                            songName = song.songName,
                                            songPath = songPath,
                                            songId = song.songId,
                                            artistName = song.artistName,
                                            imagePath = imagePath

                                        )
                                    )
                                )
                            }
                        ){ albumPath, imagePath ->
                            storedMusicEvents(StoredMusicEvent.SaveDownloadedAlbum(
                                album = DownloadedAlbum(
                                    albumName = album.albumName,
                                    albumPath = albumPath,
                                    albumId = album.albumId,
                                    artistName = album.artistName,
                                    imagePath = imagePath,
                                )
                            )
                            )
                        }
                }
                else{
                    Utilities.unDownloadAlbum(
                        "",
                    ){
                        storedMusicEvents(StoredMusicEvent.DeleteDownloadedAlbum(albumId = album.albumId))
                    }
                }

            },

            BottomSheetOption(com.fov.common_ui.R.drawable.ic_send, "Share") {
                shareAlbum(album = album, context = context)
            },
        ),
        events
      )

    }

@ExperimentalMaterialApi
fun songBottomSheet(
    song : Song,
    events : (CommonEvent) -> Unit,
    musicEvents : (MusicEvent) -> Unit,
    storedMusicEvents : (StoredMusicEvent) -> Unit,
    scope: CoroutineScope,
    bottomSheetScaffoldState : BottomSheetScaffoldState,
    context: Context,
    lifecycleOwner: LifecycleOwner,
    isDownloaded : Boolean,
    isLiked : Boolean){

    bottomSheetAction(
            scope = scope,
            bottomSheetScaffoldState,
            listOf(
                BottomSheetOption(
                     R.drawable.ic_arrow_down_circle ,
                    if(isDownloaded) "Downloaded" else "Download") {
                    if(!isDownloaded) {
                        Utilities.downloadSong(
                            context,
                            song,
                            "",
                            changeDownloadData = { downloadUrl, details, destinationFilePath ->
                                events(
                                    CommonEvent.ChangeDownloadData(
                                        DownloadData(
                                            downloadUrl = downloadUrl,
                                            details = details,
                                            destinationFilePath = destinationFilePath
                                        )
                                    )
                                )
                            }).observe(lifecycleOwner) { workInfo ->
                            if (workInfo.state.isFinished) {
                                val data = workInfo.outputData
                                val dataMap = data.keyValueMap
                                if(dataMap.containsKey("FILEPATH")){
                                    val arrPaths = dataMap["FILEPATH"] as Array<String>
                                    //save to downloadedSongsDatabase
                                    storedMusicEvents(
                                        StoredMusicEvent.SaveDownloadedSong(
                                            DownloadedSong(
                                                songName = song.songName,
                                                songPath = arrPaths[0],
                                                songId = song.songId,
                                                artistName = song.artistName,
                                                imagePath = arrPaths[1]

                                            )
                                        )
                                    )
                                    storedMusicEvents(
                                        StoredMusicEvent.UpdateSongDownloadProgress(
                                            null, song.songId
                                        )
                                    )
                                    Log.d("PROGRESS", "DONE")

                                }
                                else{
                                    //show error
                                }

                            } else {
                                val progress = workInfo.progress
                                val value = progress.getInt("progress", 1)
                                Log.d("PROGRESS", (value/100.00).toFloat().toString())
                                Log.d("PROGRESS_TEST", (2/100.00).toFloat().toString())
                                storedMusicEvents(
                                    StoredMusicEvent.UpdateSongDownloadProgress(
                                        (value/100.00).toFloat(), song.songId
                                    )
                                )
                            }

                            }
                    }
                    else{
                        Utilities.unDownloadSong(
                            "",
                        ){
                            storedMusicEvents(StoredMusicEvent.DeleteDownloadedSong(song.songId))
                        }
                    }

                },

                BottomSheetOption(com.fov.common_ui.R.drawable.ic_send, "Share") {
                    shareSong(song,context)
                },
                BottomSheetOption(com.fov.common_ui.R.drawable.ic_disc, "View Album") {
                      //musicEvents(MusicEvent.AlbumSelected(album = ))
                },
                BottomSheetOption(com.fov.common_ui.R.drawable.ic_mic, "View Artist") {

                }
            ),
            events
        )

}