package com.fov.domain.models.general

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ImagePager(
    @SerializedName("id") val id : String,
    @SerializedName("path") val path : String,
    @SerializedName("description") val description : String
)
