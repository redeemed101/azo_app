package com.fov.domain.models.shorts

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Short(
    val name: String,
    val path : String,
    val id: String,
    val type : String,
)
