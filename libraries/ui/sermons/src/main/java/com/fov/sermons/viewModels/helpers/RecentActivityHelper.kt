package com.fov.sermons.viewModels.helpers

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fov.core.utils.Constants
import com.fov.domain.database.models.RecentActivity
import com.fov.domain.interactor.music.MusicInteractor
import com.fov.sermons.models.Song
import com.fov.sermons.pagination.RecentActivitySource
import com.fov.sermons.pagination.SongsSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class RecentActivityHelper constructor(
    val musicInteractor: MusicInteractor
) {
    fun saveRecentActivity(recentActivity: RecentActivity, scope : CoroutineScope, error : (Exception) -> Unit){
        scope.launch {
            musicInteractor.insertRecentActivity(recentActivity)


        }
    }
    fun deleteRecentActivity(scope : CoroutineScope,error : (Exception) -> Unit) {
        scope.launch {
            try{
                musicInteractor.deleteRecentActivity()
            }
            catch(ex : Exception) {
                error(ex)
            }
        }
    }
    fun getRecentActivities(scope : CoroutineScope, error : (Exception) -> Unit): Flow<PagingData<RecentActivity>> {

        try {

            return Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
                RecentActivitySource(
                    musicInteractor = musicInteractor,

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