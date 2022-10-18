package com.fov.domain.remote.music

import com.fov.domain.models.music.album.AlbumsResult
import com.fov.domain.models.music.song.Song
import com.fov.domain.models.music.song.SongsResult
import com.fov.domain.utils.constants.QueryConstants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class MusicKtorService constructor(private val client: HttpClient)  {


    suspend fun getTopSongs(page: Int): SongsResult? =  client.request("Sermon/trending?page=${page}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }.body()

    suspend fun getTopAlbums(page: Int): AlbumsResult?  =  client.request("Series/trending?page=${page}&size=${QueryConstants.NUM_ROWS}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }.body()

    suspend fun getForYouSongs(page: Int): SongsResult? =  client.request("Sermon/latest?page=${page}&size=${QueryConstants.NUM_ROWS}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }.body()



    suspend fun searchSongs(search: String, page: Int): SongsResult? =  client.request("music/Song/search?search=${search}?page=${page}&size=${QueryConstants.NUM_ROWS}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }.body()



    suspend fun getSong(id: String): Song?  = client.request("Sermon/sermon/${id}"){
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }.body()
}