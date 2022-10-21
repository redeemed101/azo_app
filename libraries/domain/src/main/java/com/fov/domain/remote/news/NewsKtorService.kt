package com.fov.domain.remote.news

import com.fov.domain.models.general.ImagePagerResult
import com.fov.domain.models.music.song.SongsResult
import com.fov.domain.models.news.NewsResult
import com.fov.domain.utils.constants.QueryConstants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class NewsKtorService constructor(private val client: HttpClient)  {

    suspend fun getNews(page: Int): NewsResult? =  client.request("News/latest?page=${page}&size=${QueryConstants.NUM_ROWS}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }.body()

    suspend fun getImagePagers(page: Int): ImagePagerResult? =  client.request("Home/imagePager?page=${page}&size=${QueryConstants.NUM_ROWS}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }.body()
}