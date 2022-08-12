package com.fov.domain.cache

import com.fov.domain.database.daos.RecentActivityDao
import com.fov.domain.database.daos.RecentSongSearchDao
import com.fov.domain.database.models.RecentActivity
import com.fov.domain.database.models.RecentSongSearch
import com.fov.domain.database.models.RecentUserSearch

class RoomCache constructor(
    private val songSearchDao: RecentSongSearchDao,
    private val recentActivityDao : RecentActivityDao
) : Cache {
    override fun getRecentSongSearch() = songSearchDao.getSongs()


    override suspend fun insertRecentSongSearch(song: RecentSongSearch) {
        songSearchDao.insertAll(song)
        songSearchDao.deleteMoreThanLimit()
    }

    override suspend fun deleteRecentSongSearch(song: RecentSongSearch) {
       songSearchDao.delete(song)
    }

    override suspend fun deleteAllRecentSongSearch() {
       songSearchDao.deleteAll()
    }


    override fun getRecentActivities() = recentActivityDao.getRecentActivities()


    override suspend fun insertRecentActivity(activity: RecentActivity) {
        recentActivityDao.insertAll(activity)
        recentActivityDao.deleteMoreThanLimit()
    }

    override suspend fun deleteRecentActivity(activity: RecentActivity) {
        recentActivityDao.delete(activity)
    }

    override suspend fun deleteAllRecentActivities() {
        recentActivityDao.deleteAll()
    }




}