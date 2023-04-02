package com.fov.domain.remote.music


import com.fov.domain.models.music.song.Sermon


class MusicKtorRemote constructor(
    private val musicService: MusicKtorService,
) : MusicRemote {

    override suspend fun getTopSongs( token: String,page: Int) = musicService.getTopSongs(token,page)
    override suspend fun getTopAlbums( token: String,page: Int) = musicService.getTopAlbums(token,page)
    override suspend fun getForYouSongs( token: String,page: Int) = musicService.getForYouSongs(token,page)
    override suspend fun searchSongs( token: String,search: String, page: Int) = musicService.searchSongs(token,search,page)
    override suspend fun getSong( token: String,id: String): Sermon? = musicService.getSong(token,id)
}