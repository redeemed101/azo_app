package com.fov.domain.repositories.payment

import com.fov.domain.models.payment.ProductRequest
import com.fov.domain.models.payment.StripeCredentials
import com.fov.domain.remote.payment.PaymentRemote

class PaymentRepositoryImpl constructor(
     private val paymentRemote: PaymentRemote
): PaymentRepository{
    override suspend fun getStripeClientSecret(products: ProductRequest) = paymentRemote.getStripeClientSecret(products)
    override suspend fun getStripeCredentials() = paymentRemote.getStripeCredentials()
}