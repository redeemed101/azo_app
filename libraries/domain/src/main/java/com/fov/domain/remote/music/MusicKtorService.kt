package com.fov.domain.remote.music

import com.fov.domain.models.music.album.AlbumsResult
import com.fov.domain.models.music.song.Sermon
import com.fov.domain.models.music.song.SermonsResult
import com.fov.domain.utils.constants.QueryConstants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class MusicKtorService constructor(private val client: HttpClient)  {


    suspend fun getTopSongs( token: String,page: Int): SermonsResult? =  client.request("Sermon/trending?page=${page}&size=${QueryConstants.NUM_ROWS}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
            append("Authorization", "Bearer $token")
        }
    }.body()

    suspend fun getTopAlbums( token: String,page: Int): AlbumsResult?  =  client.request("Series/trending?page=${page}&size=${QueryConstants.NUM_ROWS}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
            append("Authorization", "Bearer $token")
        }
    }.body()

    suspend fun getForYouSongs( token: String,page: Int): SermonsResult? =  client.request("Sermon/latest?page=${page}&size=${QueryConstants.NUM_ROWS}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
            append("Authorization", "Bearer $token")
        }
    }.body()



    suspend fun searchSongs( token: String,search: String, page: Int): SermonsResult? =  client.request("music/Song/search?search=${search}?page=${page}&size=${QueryConstants.NUM_ROWS}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
            append("Authorization", "Bearer $token")
        }
    }.body()



    suspend fun getSong( token: String,id: String): Sermon?  = client.request("Sermon/sermon/${id}"){
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
            append("Authorization", "Bearer $token")
        }
    }.body()
}