package com.fov.domain.cache

import androidx.paging.PagingSource
import com.fov.domain.database.models.RecentActivity
import com.fov.domain.database.models.RecentSongSearch
import com.fov.domain.database.models.RecentUserSearch
import kotlinx.coroutines.flow.Flow

interface Cache {
     fun getRecentSongSearch() :   List<RecentSongSearch>
     suspend fun insertRecentSongSearch(song : RecentSongSearch)
     suspend fun deleteRecentSongSearch(song : RecentSongSearch)
     suspend fun deleteAllRecentSongSearch()
     fun getRecentActivities(): List<RecentActivity>
     suspend fun insertRecentActivity(activity: RecentActivity)
     suspend fun deleteRecentActivity(activity: RecentActivity)
     suspend fun deleteAllRecentActivities()
}