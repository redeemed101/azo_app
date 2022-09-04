package com.fov.domain.remote.payment

import com.fov.domain.models.payment.Product
import com.fov.domain.models.payment.ProductRequest

interface PaymentRemote {
    suspend fun getStripeClientSecret(products : ProductRequest) : String?
}