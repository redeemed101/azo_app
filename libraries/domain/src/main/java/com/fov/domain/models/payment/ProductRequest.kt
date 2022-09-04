package com.fov.domain.models.payment

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProductRequest(
    @SerializedName("products") val products : List<Product>
)