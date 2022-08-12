package com.fov.domain.remote.music


import com.fov.domain.models.music.album.AlbumsResult
import com.fov.domain.models.music.song.Song
import com.fov.domain.models.music.song.SongsResult

interface MusicRemote {

    suspend fun getTopSongs(page: Int): SongsResult?
    suspend fun getTopAlbums(page: Int): AlbumsResult?
    suspend fun getForYouSongs(page: Int): SongsResult?
    suspend fun searchSongs(search: String, page: Int): SongsResult?
    suspend fun getSong(id: String): Song?
}