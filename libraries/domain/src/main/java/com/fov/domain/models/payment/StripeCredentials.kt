package com.fov.domain.models.payment

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class StripeCredentials(
    @SerializedName("publishableKey") val publishableKey : String,
    @SerializedName("stripeAccountId") val stripeAccountId : String
)
