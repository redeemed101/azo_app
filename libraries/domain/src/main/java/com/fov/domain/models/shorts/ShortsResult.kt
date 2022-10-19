package com.fov.domain.models.shorts

import androidx.annotation.Keep
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class ShortsResult(
    val shorts : List<Short> = emptyList()
)
