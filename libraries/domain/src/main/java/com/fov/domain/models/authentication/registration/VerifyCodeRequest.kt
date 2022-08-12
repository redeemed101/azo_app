package com.fov.domain.models.authentication.registration
import kotlinx.serialization.Serializable

@Serializable
data class VerifyCodeRequest(
    val userId : String,
    val code : String
)
