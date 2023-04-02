package com.fov.domain.repositories.music

import com.fov.domain.database.models.RecentActivity
import com.fov.domain.database.models.RecentSongSearch
import com.fov.domain.models.music.album.AlbumsResult
import com.fov.domain.models.music.song.Sermon
import com.fov.domain.models.music.song.SermonsResult

interface SermonRepository {



    suspend fun getTopSongs(
        page : Int
    ): SermonsResult?

    suspend fun getSong(
        id : String
    ): Sermon?

    suspend fun getTopAlbums(page: Int): AlbumsResult?
    suspend fun getForYouSongs(page: Int): SermonsResult?
    suspend fun searchSongs(search: String, page: Int) : SermonsResult?

    fun recentSongSearches() :  List<RecentSongSearch>
    suspend fun insertRecentSongSearch(song: RecentSongSearch)
    suspend fun deleteRecentSongSearch()
    suspend fun recentActivities() : List<RecentActivity>
    suspend fun insertRecentActivity(activity: RecentActivity)
    suspend fun deleteRecentActivity()
}