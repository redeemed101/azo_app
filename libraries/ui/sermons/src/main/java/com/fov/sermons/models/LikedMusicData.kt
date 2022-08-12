package com.fov.sermons.models

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class LikedMusicData(
    val likedSongs : Flow<PagingData<Song>> = flowOf(PagingData.from(emptyList())),
    val likedAlbums :  Flow<PagingData<Album>> = flowOf(PagingData.from(emptyList())),
)
