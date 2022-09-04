package com.fov.domain.interactors.payment

import com.fov.domain.models.payment.ProductRequest
import com.fov.domain.repositories.payment.PaymentRepository

class PaymentInteractor  constructor(
    private val paymentRepository: PaymentRepository
) {
    suspend fun getStripeClientSecret(products: ProductRequest) = paymentRepository.getStripeClientSecret(products = products)

    suspend fun getStripeCredentials() = paymentRepository.getStripeCredentials()
}