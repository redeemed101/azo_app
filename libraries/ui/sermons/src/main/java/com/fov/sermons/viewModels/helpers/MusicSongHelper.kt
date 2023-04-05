package com.fov.sermons.viewModels.helpers

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fov.common_ui.utils.constants.Constants
import com.fov.common_ui.utils.constants.SongRequestType
import com.fov.domain.interactors.music.MusicInteractor
import com.fov.sermons.models.Song
import com.fov.sermons.pagination.SongsSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class MusicSongHelper constructor(
    val musicInteractor: MusicInteractor,
    val accessToken : String
) {

   suspend fun getSong(songId : String) : Song? {
       val song : Song?
       val s = musicInteractor.getSong(accessToken,songId)

       return s?.let { Song.ModelMapper.from(it) }

   }
    fun getTopSongs(scope : CoroutineScope, error : (Exception) -> Unit): Flow<PagingData<Song>> {

        try {

            return Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
                SongsSource(
                    musicInteractor = musicInteractor,
                    accessToken = accessToken,
                    songRequestType = SongRequestType.TOP_SONGS,
                )
            }.flow
                .cachedIn(scope)


        }
        catch(ex : Exception) {
            error(ex)
        }
        return flowOf(PagingData.from(emptyList()))
    }
    fun getForYouSongs(scope : CoroutineScope, error : (Exception) -> Unit): Flow<PagingData<Song>> {

        try {

            return Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
                SongsSource(
                    musicInteractor = musicInteractor,
                    accessToken = accessToken,
                    songRequestType = SongRequestType.FOR_YOU,
                )
            }.flow
                .cachedIn(scope)


        }
        catch(ex : Exception) {
            error(ex)
        }
        return flowOf(PagingData.from(emptyList()))
    }

   fun getRecentSongSearch(scope : CoroutineScope,error : (Exception) -> Unit): Flow<PagingData<Song>> {

        try {

            return Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
                SongsSource(
                    musicInteractor = musicInteractor,
                    accessToken = accessToken,
                    songRequestType = SongRequestType.RECENT_SEARCH,
                )
            }.flow
                .cachedIn(scope)


        }
        catch(ex : Exception) {
          error(ex)
        }
        return flowOf(PagingData.from(emptyList()))
    }
    fun saveRecentSongSearch(song: Song,scope : CoroutineScope,error : (Exception) -> Unit){
        scope.launch {
            musicInteractor.insertRecentSongSearch(
                Song.ModelMapper.toRecentSearch(song)
            )


        }
    }
    fun deleteRecentSongSearch(scope : CoroutineScope,error : (Exception) -> Unit) {
        scope.launch {
            try{
                musicInteractor.deleteRecentSongSearch()
            }
            catch(ex : Exception) {
                error(ex)
            }
        }
    }
   fun getGenreSongs(id : String,scope : CoroutineScope, error : (Exception) -> Unit): Flow<PagingData<Song>> {

        try {

            return Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
                SongsSource(
                    musicInteractor = musicInteractor,
                    accessToken = accessToken,
                    songRequestType = SongRequestType.GENRE_SONGS,
                    genreId = id
                )
            }.flow
                .cachedIn(scope)


        }
        catch(ex : Exception) {
           error(ex)
        }
        return flowOf(PagingData.from(emptyList()))
    }
    fun getLikedSongs(id : String,scope : CoroutineScope, error : (Exception) -> Unit): Flow<PagingData<Song>> {

        try {

            return Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
                SongsSource(
                    musicInteractor = musicInteractor,
                    accessToken = accessToken,
                    songRequestType = SongRequestType.LIKED_SONGS,
                    userId = id
                )
            }.flow
                .cachedIn(scope)


        }
        catch(ex : Exception) {
            error(ex)
        }
        return flowOf(PagingData.from(emptyList()))
    }

    fun getSongsByYear(year:Int,scope : CoroutineScope, error : (Exception) -> Unit): Flow<PagingData<Song>> {

        try {

            return Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
                SongsSource(
                    musicInteractor = musicInteractor,
                    accessToken = accessToken,
                    songRequestType = SongRequestType.YEAR_SONGS,
                    year = year
                )
            }.flow
                .cachedIn(scope)


        }
        catch(ex : Exception) {
            error(ex)
        }
        return flowOf(PagingData.from(emptyList()))
    }

     fun getNewSongs(scope : CoroutineScope, error : (Exception) -> Unit): Flow<PagingData<Song>> {

        try {

            return Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
                SongsSource(
                    musicInteractor = musicInteractor,
                    accessToken = accessToken,
                    songRequestType = SongRequestType.TOP_SONGS,
                )
            }.flow
                .cachedIn(scope)


        }
        catch(ex : Exception) {
            error(ex)
        }
        return flowOf(PagingData.from(emptyList()))
    }
    fun searchSongs(search : String,scope : CoroutineScope, error : (Exception) -> Unit) : Flow<PagingData<Song>>{



        try {

            return Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
                SongsSource(
                    musicInteractor = musicInteractor,
                    SongRequestType.ALL_SONGS,
                    search
                )
            }.flow
                .cachedIn(scope)


        }
        catch(ex : Exception) {
           error(ex)
        }
        return flowOf(PagingData.from(emptyList()))

    }
}