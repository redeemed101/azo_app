package com.fov.domain.repositories.music

import com.fov.domain.cache.Cache
import com.fov.domain.database.models.RecentActivity
import com.fov.domain.database.models.RecentSongSearch
import com.fov.domain.remote.music.MusicRemote
import com.fov.domain.repositories.music.SermonRepository


class SermonRepositoryImpl constructor(
    private val musicRemote : MusicRemote,
    private val cache: Cache
) : SermonRepository {

    override suspend fun getTopSongs( token: String,page: Int) = musicRemote.getTopSongs(token,page)
    override suspend fun getSong( token: String,id: String) = musicRemote.getSong(token,id)

    override suspend fun getTopAlbums( token: String,nextPage: Int) = musicRemote.getTopAlbums(token,nextPage)
    override suspend fun getForYouSongs( token: String,page: Int) = musicRemote.getForYouSongs(token,page)
    override suspend fun searchSongs( token: String,search: String, page: Int) = musicRemote.searchSongs(token,search,page)
    override fun recentSongSearches() = cache.getRecentSongSearch()


    override suspend fun insertRecentSongSearch(song: RecentSongSearch) {
        return cache.insertRecentSongSearch(song)
    }

    override suspend fun deleteRecentSongSearch() = cache.deleteAllRecentSongSearch()
    override suspend fun recentActivities() = cache.getRecentActivities()

    override suspend fun insertRecentActivity(activity: RecentActivity) = cache.insertRecentActivity(activity)

    override suspend fun deleteRecentActivity() = cache.deleteAllRecentActivities()
}