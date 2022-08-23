package com.fov.domain.utils.utilities

import io.ktor.client.plugins.*


class Failure{
    companion object{
        fun httpErrorInternalServerError(serverResponseException: ServerResponseException): String {
            return "Server errror ${serverResponseException.message}"
        }

        fun HttpErrorBadRequest(clientRequestException: ClientRequestException): String {
                return "Bad Request error $clientRequestException"
        }

        fun HttpErrorUnauthorized(clientRequestException: ClientRequestException): String {
              return "Not Authorized"
        }

        fun HttpErrorForbidden(clientRequestException: ClientRequestException): String {
             return "Forbidden"
        }

        fun HttpErrorNotFound(clientRequestException: ClientRequestException): String {
           return "Not Found"
        }

        fun HttpError(exception: Exception): String {
            return "Error processing request"
        }

        fun GenericError(exception: Exception): String {
              return "Error Processing Request"
        }

    }
}