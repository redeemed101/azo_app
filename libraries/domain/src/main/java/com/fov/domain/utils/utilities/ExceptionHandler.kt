package com.fov.domain.utils.utilities


import com.fov.domain.models.general.GeneralError
import com.google.gson.Gson
import io.ktor.client.statement.*
import io.ktor.utils.io.*

class ExceptionHandler {

    companion object {
        suspend fun getError(response: HttpResponse): GeneralError {

            response.content.readUTF8Line()?.let {

                var gson = Gson()

                return gson.fromJson(it, GeneralError::class.java);
            }
            throw IllegalArgumentException("not a parsable error")
        }
    }
}