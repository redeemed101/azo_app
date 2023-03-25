package com.fov.domain.models.authentication.registration

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class Error (

    @SerializedName("status_code") val StatusCode : String,
    @SerializedName("message") val Message : String
)


