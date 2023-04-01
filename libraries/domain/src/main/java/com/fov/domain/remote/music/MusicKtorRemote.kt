package com.fov.domain.remote.music


import com.fov.domain.models.music.song.Sermon


class MusicKtorRemote constructor(
    private val musicService: MusicKtorService,
) : MusicRemote {

    override suspend fun getTopSongs(page: Int) = musicService.getTopSongs(page)
    override suspend fun getTopAlbums(page: Int) = musicService.getTopAlbums(page)
    override suspend fun getForYouSongs(page: Int) = musicService.getForYouSongs(page)
    override suspend fun searchSongs(search: String, page: Int) = musicService.searchSongs(search,page)
    override suspend fun getSong(id: String): Sermon? = musicService.getSong(id)
}