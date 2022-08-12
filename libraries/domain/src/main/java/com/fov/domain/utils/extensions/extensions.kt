package com.fov.domain.utils.extensions

import com.fov.domain.utils.utilities.Failure
import io.ktor.client.features.*

fun Exception.toCustomExceptions() = when (this) {
    is ServerResponseException -> Failure.httpErrorInternalServerError(this)
    is ClientRequestException ->
        when (this.response.status.value) {
            400 -> Failure.HttpErrorBadRequest(this)
            401 -> Failure.HttpErrorUnauthorized(this)
            403 -> Failure.HttpErrorForbidden(this)
            404 -> Failure.HttpErrorNotFound(this)
            else -> Failure.HttpError(this)
        }
    is RedirectResponseException -> Failure.HttpError(this)
    else -> Failure.GenericError(this)
}

