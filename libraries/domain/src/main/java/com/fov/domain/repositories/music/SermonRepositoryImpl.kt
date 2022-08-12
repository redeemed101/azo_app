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

    override suspend fun getTopSongs(page: Int) = musicRemote.getTopSongs(page)
    override suspend fun getSong(id: String) = musicRemote.getSong(id)

    override suspend fun getTopAlbums(nextPage: Int) = musicRemote.getTopAlbums(nextPage)
    override suspend fun getForYouSongs(page: Int) = musicRemote.getForYouSongs(page)
    override suspend fun searchSongs(search: String, page: Int) = musicRemote.searchSongs(search,page)
    override fun recentSongSearches() = cache.getRecentSongSearch()


    override suspend fun insertRecentSongSearch(song: RecentSongSearch) {
        return cache.insertRecentSongSearch(song)
    }

    override suspend fun deleteRecentSongSearch() = cache.deleteAllRecentSongSearch()
    override suspend fun recentActivities() = cache.getRecentActivities()

    override suspend fun insertRecentActivity(activity: RecentActivity) = cache.insertRecentActivity(activity)

    override suspend fun deleteRecentActivity() = cache.deleteAllRecentActivities()
}