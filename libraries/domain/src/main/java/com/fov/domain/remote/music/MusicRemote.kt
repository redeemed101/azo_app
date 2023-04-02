package com.fov.domain.remote.music


import com.fov.domain.models.music.album.AlbumsResult
import com.fov.domain.models.music.song.Sermon
import com.fov.domain.models.music.song.SermonsResult

interface MusicRemote {

    suspend fun getTopSongs( token: String,page: Int): SermonsResult?
    suspend fun getTopAlbums( token: String,page: Int): AlbumsResult?
    suspend fun getForYouSongs( token: String,page: Int): SermonsResult?
    suspend fun searchSongs( token: String,search: String, page: Int): SermonsResult?
    suspend fun getSong( token: String,id: String): Sermon?
}