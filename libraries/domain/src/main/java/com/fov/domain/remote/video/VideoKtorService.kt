package com.fov.domain.remote.video

import com.fov.domain.models.music.video.VideosResult
import com.fov.domain.models.shorts.ShortsResult
import com.fov.domain.utils.constants.QueryConstants
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class VideoKtorService constructor(private val client: HttpClient)  {

    suspend fun getVideos(token:String,page: Int): VideosResult? {
        val response = client.request("Video?page=${page}&size=${QueryConstants.NUM_ROWS}") {
            method = HttpMethod.Get
            headers {
                append("Content-Type", "application/json")
                append("Authorization", "Bearer $token")
            }
        }.bodyAsText()
        return Gson().fromJson(response, VideosResult::class.java)
    }

        suspend fun getShorts(token:String,page: Int): ShortsResult? = client.request("Shorts?page=${page}&size=${QueryConstants.NUM_ROWS}") {
            method = HttpMethod.Get
            headers {
                append("Content-Type", "application/json")
                append("Authorization", "Bearer $token")
            }
        }.body()
    }
