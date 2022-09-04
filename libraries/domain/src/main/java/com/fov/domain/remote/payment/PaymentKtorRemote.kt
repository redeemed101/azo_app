package com.fov.domain.remote.payment

import com.fov.domain.models.payment.Product
import com.fov.domain.models.payment.ProductRequest
import com.fov.domain.models.payment.StripeCredentials

class PaymentKtorRemote constructor(
    private val paymentKtorService: PaymentKtorService
)
    : PaymentRemote{
    override suspend fun getStripeClientSecret(products: ProductRequest) = paymentKtorService.getStripeClientSecret(products)
    override suspend fun getStripeCredentials() = paymentKtorService.getStripeCredentials()
}