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
import com.fov.domain.remote.mock.payment.PaymentStripeCredentialsMockResponse
import com.fov.domain.remote.mock.payment.PaymentStripeCustomerIdResponse
import com.fov.domain.remote.mock.video.VideosMockResponse
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
                        "/payment/Stripe/getClientSecret" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(PaymentStripeCustomerIdResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/payment/Stripe/credentials" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(PaymentStripeCredentialsMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/User/signup" -> {

                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(SignUpMockResponse(), HttpStatusCode.OK, responseHeaders)

                        }
                        "/User/socialSignin" -> {

                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(LoginMockResponse(), HttpStatusCode.OK, responseHeaders)

                        }
                        "/User/logout" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(LogoutMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/User/reset" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(LoginMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/User/signin" -> {

                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(LoginMockResponse(), HttpStatusCode.OK, responseHeaders)

                        }
                        "/User/forgotPassword" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }


                        "/Series/trending" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            val page = request.url.parameters["page"]
                            respond(AlbumsMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "Video" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            val page = request.url.parameters["page"]
                            respond(VideosMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/Sermon/trending" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            val page = request.url.parameters["page"]
                            respond(SongsMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/Sermon/sermon/1234" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))

                            respond(SongMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/Sermon/latest" ->{
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
                        "/User/verifyUserCode" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/User/passwordchange" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/User/resendUserCode" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/User/deleteAccount" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/User/disableAccount" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }

                        "/music/Song/search" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(SongsMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }

                        "/User/notifications/sdfsdfsdf" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(UserNotificationsMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/users/User/numNotifications/sdfsdfsdf" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(UserNumNotificationsMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/User/notifications/unread/sdfsdfsdf" ->{
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