package com.fov.domain.cache


import com.fov.domain.database.daos.RecentActivityDao
import com.fov.domain.database.daos.RecentSongSearchDao
import com.fov.domain.database.models.ActivityType
import com.fov.domain.database.models.RecentActivity
import com.fov.domain.database.models.RecentSongSearch

class CacheTest constructor(
    private val songSearchDao: RecentSongSearchDao,
    private val recentActivityDao : RecentActivityDao
) : Cache {
    override fun getRecentSongSearch(): List<RecentSongSearch> {
          return List(10){ num ->
              RecentSongSearch(
                  dbId = num.toLong(),
                  songId = "234$num",
                  artistName = "Apostle Ziba",
                  songName = "Born Ready $num",
                  songLength = "2:40",
                  artwork = "https://picsum.photos/200",
                  description = "This is a good song",
                  previewPath = "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3",
                  path = "c",
                  likes = 0,
                  streams = 0

              )
          }
    }

    override suspend fun insertRecentSongSearch(song: RecentSongSearch) {

    }

    override suspend fun deleteRecentSongSearch(song: RecentSongSearch) {

    }

    override suspend fun deleteAllRecentSongSearch() {

    }



    override fun getRecentActivities(): List<RecentActivity> {
        return List(20){ num ->
            RecentActivity(
                dbId = 0,
                id = "$num",
                title = "Lubadini $num",
                subTitle = "This Luba $num",
                type = ActivityType.SONG.type,
                image = "https://picsum.photos/200"

            )
        }
    }

    override suspend fun insertRecentActivity(activity: RecentActivity) {

    }

    override suspend fun deleteRecentActivity(activity: RecentActivity) {

    }

    override suspend fun deleteAllRecentActivities() {

    }
}