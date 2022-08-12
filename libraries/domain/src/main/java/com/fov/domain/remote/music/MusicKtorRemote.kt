package com.fov.domain.remote.music


import com.fov.domain.models.music.song.Song
import com.fov.domain.remote.music.MusicKtorService
import com.fov.domain.remote.music.MusicRemote


class MusicKtorRemote constructor(
    private val musicService: MusicKtorService,
) : MusicRemote {

    override suspend fun getTopSongs(page: Int) = musicService.getToSongs(page)
    override suspend fun getTopAlbums(page: Int) = musicService.getTopAlbums(page)
    override suspend fun getForYouSongs(page: Int) = musicService.getForYouSongs(page)
    override suspend fun searchSongs(search: String, page: Int) = musicService.searchSongs(search,page)
    override suspend fun getSong(id: String): Song? = musicService.getSong(id)
}