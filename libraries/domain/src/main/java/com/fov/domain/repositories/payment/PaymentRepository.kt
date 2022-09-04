package com.fov.domain.repositories.payment

import com.fov.domain.models.payment.Product
import com.fov.domain.models.payment.ProductRequest

interface PaymentRepository {
    suspend fun getStripeClientSecret(products: ProductRequest): String?
}