package com.fov.domain.remote.ktor

import android.util.Log
//import com.fov.domain.BuildConfig
import com.fov.domain.exceptions.ServerException
import com.fov.domain.utils.utilities.ExceptionHandler
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class KtorClient {

    companion object {

        private const val TIME_OUT = 60_000

        val ktorHttpClient = HttpClient(Android) {



            defaultRequest {
                host = "{BuildConfig.URL}"
                url {
                   protocol = URLProtocol.HTTPS
                }
            }

            HttpResponseValidator {
                validateResponse {
                    val statusCode = it.status.value
                    when (statusCode) {
                        in 300..399 -> print(it.content.toString())
                        in 400..499 -> {
                            print(it.content.toString())
                            throw ClientRequestException(it,it.content.toString())
                        }
                        in 500..599 -> print(it.content.toString())
                    }
                }
                handleResponseException { exception ->

                   val error = when (exception) {

                        is ClientRequestException -> ExceptionHandler.getError(exception.response)
                        else -> throw Exception("Something went wrong ${exception.message}") //or do whatever you need
                    }
                    throw ServerException(error.errorDescription)
                    /*val clientException = exception as? ClientRequestException ?: return@handleResponseException
                    val exceptionResponse = exception.response
                     if (exceptionResponse.status == HttpStatusCode.NotFound) {
                        val exceptionResponseText = exceptionResponse.readText()
                        throw MissingPageException(exceptionResponse, exceptionResponseText)
                    }*/
                }
            }

            install(JsonFeature) {
                serializer = KotlinxSerializer(Json.Default)

                engine {
                    connectTimeout = TIME_OUT
                    socketTimeout = TIME_OUT
                }
            }

            install(Auth) {
                basic {
                    sendWithoutRequest = true
                    username = "fov@enyo2021"
                    password = "fov@Namande@1190"
                }
            }




            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("Logger Ktor =>", message)
                    }

                }
                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("HTTP status:", "${response.status.value}")
                }
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                //header("","")
            }
        }
    }
}
