package com.fov.domain.models.authentication.registration

import com.fov.domain.models.authentication.registration.Error
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class ErrorResult (

    @SerializedName("success") val success : Boolean,
    @SerializedName("errors") val errors : List<Error>
)