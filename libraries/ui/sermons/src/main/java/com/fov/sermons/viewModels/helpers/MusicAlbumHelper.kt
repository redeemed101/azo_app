package com.fov.sermons.viewModels.helpers

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fov.common_ui.utils.constants.AlbumRequestType
import com.fov.common_ui.utils.constants.Constants
import com.fov.domain.interactors.music.MusicInteractor
import com.fov.sermons.models.Album
import com.fov.sermons.pagination.AlbumsSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MusicAlbumHelper constructor(
    val musicInteractor: MusicInteractor,
    val accessToken : String
) {
    fun getNewAlbums(scope : CoroutineScope, error : (Exception) -> Unit) : Flow<PagingData<Album>> {

        try {

            return Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
                AlbumsSource(
                    musicInteractor = musicInteractor,
                    accessToken = accessToken,
                    albumRequestType = AlbumRequestType.TOP_ALBUMS,
                )
            }.flow
                .cachedIn(scope)


        }
        catch(ex : Exception) {
            error(ex)
        }
        return flowOf(PagingData.from(emptyList()))
    }
     fun getGenreAlbums(id : String,scope : CoroutineScope,error : (Exception) -> Unit) : Flow<PagingData<Album>> {

        try {

            return Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
                AlbumsSource(
                    musicInteractor = musicInteractor,
                    accessToken = accessToken,
                    albumRequestType = AlbumRequestType.GENRE_ALBUMS,
                    genreId  = id
                )
            }.flow
                .cachedIn(scope)


        }
        catch(ex : Exception) {
           error(ex)
        }
        return flowOf(PagingData.from(emptyList()))
    }
    fun getUserLikedAlbums(id : String,scope : CoroutineScope,error : (Exception) -> Unit) : Flow<PagingData<Album>> {

        try {

            return Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
                AlbumsSource(
                    musicInteractor = musicInteractor,
                    accessToken = accessToken,
                    albumRequestType = AlbumRequestType.LIKED_ALBUMS,
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
}