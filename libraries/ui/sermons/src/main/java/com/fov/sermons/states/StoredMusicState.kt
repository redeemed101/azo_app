package com.fov.sermons.states

import androidx.paging.PagingData
import com.fov.domain.database.models.DownloadedAlbum
import com.fov.domain.database.models.DownloadedSong
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class StoredMusicState (
    val isLoading: Boolean = false,
    val songDownloadProgress :  MutableMap<String, Float?> = mutableMapOf(),
    val downloadedSongs : Flow<PagingData<DownloadedSong>> = flowOf(PagingData.from(emptyList())),
    val downloadedAlbums : Flow<PagingData<DownloadedAlbum>> = flowOf(PagingData.from(emptyList())),
    val error: String? = null
){
     companion object {
         fun initialise(): StoredMusicState = StoredMusicState()
     }
     fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()
     class Builder(private val state: StoredMusicState) {
         var loading = state.isLoading
         var downloadedSongs = state.downloadedSongs
         var downloadedAlbums  = state.downloadedAlbums
         var songDownloadProgress = state.songDownloadProgress
         var error = state.error
         fun build(): StoredMusicState {

             return StoredMusicState(
                 loading,
                 songDownloadProgress,
                 downloadedSongs,
                 downloadedAlbums,
                 error
             )
         }
     }
 }