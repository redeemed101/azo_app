package com.fov.domain.remote.ktor

import android.util.Log
import com.fov.domain.BuildConfig
import com.fov.domain.exceptions.ServerException
import com.fov.domain.utils.utilities.ExceptionHandler
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class KtorClient {

    companion object {

        private const val TIME_OUT = 600_000

        val ktorHttpClient = HttpClient(Android) {

            install(HttpTimeout) {
                requestTimeoutMillis = TIME_OUT.toLong()
            }

            defaultRequest {
                host = "${BuildConfig.FOV_URL}"
                url {
                   protocol = URLProtocol.HTTPS
                }
            }
            expectSuccess = true
            HttpResponseValidator {
                validateResponse { response ->
                    val statusCode = response.status
                    when (statusCode.value) {
                        in 300..399 -> print(response.bodyAsText())
                        in 400..499 -> {
                            print(response.bodyAsText())
                            throw ClientRequestException(response,response.bodyAsText())
                        }
                        in 500..599 -> {
                            print(response.bodyAsText())
                            val gson = Gson()
                            val error = gson.fromJson(response.bodyAsText(),com.fov.domain.models.authentication.registration.Error::class.java)
                            throw ServerException(error.Message)
                        }
                    }
                }
                handleResponseExceptionWithRequest { exception,httpRequest ->
                    val clientException = exception as? ClientRequestException ?: return@handleResponseExceptionWithRequest
                    val exceptionResponse = clientException.response
                   val error = when (httpRequest) {

                        is HttpRequest -> ExceptionHandler.getError(exceptionResponse)
                        else -> throw Exception("Something went wrong ${exceptionResponse.bodyAsText()}") //or do whatever you need
                    }
                    throw ServerException(error.errorDescription)

                }
            }



            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }

            install(Auth) {
                basic {
                    sendWithoutRequest { request ->
                        request.url.host == "0.0.0.0"
                    }
                    credentials {
                        BasicAuthCredentials(username = "",
                            password = "")
                    }
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
                    Log.d("HTTP body:", "${response.bodyAsText()}")
                }
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                //header("","")
            }
        }
    }
}
