package com.fov.domain.models.authentication
import kotlinx.serialization.Serializable

@Serializable
data class GeneralResult(
    val success : Boolean,
    val message : String
)
