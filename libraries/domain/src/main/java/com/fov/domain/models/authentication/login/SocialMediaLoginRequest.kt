package com.fov.domain.models.authentication.login

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SocialMediaLoginRequest(

    @SerializedName("fullName") val fullName : String,
    @SerializedName("userName") val userName : String,
    @SerializedName("email") val email : String,
    @SerializedName("service") val service : String,
    @SerializedName("token") val token : String,
    @SerializedName("isFirstTime") val isFirstTime : Boolean,
    @SerializedName("profileImageUrl") val  profileImageUrl: String? = null
)
