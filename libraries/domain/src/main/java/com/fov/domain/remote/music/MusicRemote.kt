package com.fov.domain.remote.music


import com.fov.domain.models.music.album.AlbumsResult
import com.fov.domain.models.music.song.Sermon
import com.fov.domain.models.music.song.SermonsResult

interface MusicRemote {

    suspend fun getTopSongs(page: Int): SermonsResult?
    suspend fun getTopAlbums(page: Int): AlbumsResult?
    suspend fun getForYouSongs(page: Int): SermonsResult?
    suspend fun searchSongs(search: String, page: Int): SermonsResult?
    suspend fun getSong(id: String): Sermon?
}