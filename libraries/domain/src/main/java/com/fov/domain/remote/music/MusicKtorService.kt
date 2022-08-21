package com.fov.domain.remote.music

import com.fov.domain.models.music.album.AlbumsResult
import com.fov.domain.models.music.song.Song
import com.fov.domain.models.music.song.SongsResult
import com.fov.domain.utils.constants.QueryConstants
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class MusicKtorService constructor(private val client: HttpClient)  {


    suspend fun getToSongs(page: Int): SongsResult? =  client.request("music/Song/topSongs?page=${page}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }

    suspend fun getTopAlbums(page: Int): AlbumsResult?  =  client.request("music/Album/topAlbums?page=${page}&size=${QueryConstants.NUM_ROWS}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }

    suspend fun getForYouSongs(page: Int): SongsResult? =  client.request("music/Song/forYou?page=${page}&size=${QueryConstants.NUM_ROWS}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }



    suspend fun searchSongs(search: String, page: Int): SongsResult? =  client.request("music/Song/search?search=${search}?page=${page}&size=${QueryConstants.NUM_ROWS}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }



    suspend fun getSong(id: String): Song?  = client.request("music/Song/byId/${id}"){
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }
}