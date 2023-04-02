package com.fov.domain.repositories.music

import com.fov.domain.database.models.RecentActivity
import com.fov.domain.database.models.RecentSongSearch
import com.fov.domain.models.music.album.AlbumsResult
import com.fov.domain.models.music.song.Sermon
import com.fov.domain.models.music.song.SermonsResult

interface SermonRepository {



    suspend fun getTopSongs(
        token: String,
        page : Int
    ): SermonsResult?

    suspend fun getSong(
        token: String,
        id : String
    ): Sermon?

    suspend fun getTopAlbums( token: String,page: Int): AlbumsResult?
    suspend fun getForYouSongs( token: String,page: Int): SermonsResult?
    suspend fun searchSongs( token: String,search: String, page: Int) : SermonsResult?

    fun recentSongSearches() :  List<RecentSongSearch>
    suspend fun insertRecentSongSearch(song: RecentSongSearch)
    suspend fun deleteRecentSongSearch()
    suspend fun recentActivities() : List<RecentActivity>
    suspend fun insertRecentActivity(activity: RecentActivity)
    suspend fun deleteRecentActivity()
}