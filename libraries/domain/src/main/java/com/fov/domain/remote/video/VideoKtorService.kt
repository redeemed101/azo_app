package com.fov.domain.remote.video

import com.fov.domain.models.music.video.VideosResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class VideoKtorService constructor(private val client: HttpClient)  {

    suspend fun getVideos(page: Int): VideosResult? = client.request("music/Video/getVideos?page=${page}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }.body()
}