package com.fov.domain.repositories.music

import androidx.paging.PagingSource
import com.fov.domain.database.models.RecentActivity
import com.fov.domain.database.models.RecentSongSearch
import com.fov.domain.database.models.RecentUserSearch
import com.fov.domain.models.music.album.AlbumsResult
import com.fov.domain.models.music.song.Song
import com.fov.domain.models.music.song.SongsResult
import kotlinx.coroutines.flow.Flow

interface SermonRepository {



    suspend fun getTopSongs(
        page : Int
    ): SongsResult?

    suspend fun getSong(
        id : String
    ): Song?

    suspend fun getTopAlbums(page: Int): AlbumsResult?
    suspend fun getForYouSongs(page: Int): SongsResult?
    suspend fun searchSongs(search: String, page: Int) : SongsResult?

    fun recentSongSearches() :  List<RecentSongSearch>
    suspend fun insertRecentSongSearch(song: RecentSongSearch)
    suspend fun deleteRecentSongSearch()
    suspend fun recentActivities() : List<RecentActivity>
    suspend fun insertRecentActivity(activity: RecentActivity)
    suspend fun deleteRecentActivity()
}