package com.fov.domain.remote.mock

import com.fov.domain.remote.mock.authentication.login.LoginMockResponse
import com.fov.domain.remote.mock.authentication.login.LogoutMockResponse
import com.fov.domain.remote.mock.authentication.registration.GeneralMockResponse
import com.fov.domain.remote.mock.authentication.registration.SignUpMockResponse
import com.fov.domain.remote.mock.authentication.users.UserNotificationsMockResponse
import com.fov.domain.remote.mock.authentication.users.UserNumNotificationsMockResponse
import com.fov.domain.remote.mock.music.AlbumsMockResponse
import com.fov.domain.remote.mock.music.SongMockResponse
import com.fov.domain.remote.mock.music.SongsMockResponse
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class KtorMockClient {
    companion object {
        private const val TIME_OUT = 60_000

        val ktorHttpClient = HttpClient(MockEngine) {
            engine {
                /*addHandler { request ->
                    error("Unhandled ${request.url.encodedPath}")
                }*/
                addHandler { request ->
                    when (request.url.encodedPath) {
                        "/users/User/signup" -> {

                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(SignUpMockResponse(), HttpStatusCode.OK, responseHeaders)

                        }
                        "/users/User/socialSignin" -> {

                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(LoginMockResponse(), HttpStatusCode.OK, responseHeaders)

                        }
                        "/users/User/logout" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(LogoutMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/users/User/reset" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(LoginMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/users/User/signin" -> {

                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(LoginMockResponse(), HttpStatusCode.OK, responseHeaders)

                        }
                        "/users/User/forgotPassword" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }


                        "/music/Album/topAlbums" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            val page = request.url.parameters["page"]
                            respond(AlbumsMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/music/Song/topSongs" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            val page = request.url.parameters["page"]
                            respond(SongsMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/music/Song/byId/1234" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))

                            respond(SongMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/music/Song/forYou" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            val page = request.url.parameters["page"]
                            respond(SongsMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/users/User/followUsers" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))

                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/users/User/verifyUserCode" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/users/User/passwordchange" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/users/User/resendUserCode" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/users/User/deleteAccount" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/users/User/disableAccount" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }

                        "/music/Song/search" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(SongsMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }

                        "/users/User/notifications/sdfsdfsdf" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(UserNotificationsMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/users/User/numNotifications/sdfsdfsdf" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(UserNumNotificationsMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/users/User/notifications/unread/sdfsdfsdf" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(UserNotificationsMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        else ->{
                            error("Unhandled ${request.url.encodedPath}")
                        }
                    }
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}