package com.fov.domain.models.authentication.registration

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class Error (

    @SerializedName("code") val code : String,
    @SerializedName("description") val description : String
)
