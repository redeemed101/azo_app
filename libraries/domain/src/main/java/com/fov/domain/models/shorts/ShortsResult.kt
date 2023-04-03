package com.fov.domain.models.shorts

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class ShortsResult(
    @SerializedName("shorts") val shorts : List<Short> = emptyList()
)
