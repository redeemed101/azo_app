package com.fov.sermons.models

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class GenreData(
    val genre : Genre,
    val songs : Flow<PagingData<Song>> = flowOf(PagingData.from(emptyList())),
    val albums : Flow<PagingData<Album>>  = flowOf(PagingData.from(emptyList()))
)
