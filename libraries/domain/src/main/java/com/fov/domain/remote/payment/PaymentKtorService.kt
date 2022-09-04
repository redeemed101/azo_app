package com.fov.domain.remote.payment

import com.fov.domain.models.payment.Product
import com.fov.domain.models.payment.ProductRequest
import com.fov.domain.models.payment.StripeCredentials
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class PaymentKtorService constructor(private val client: HttpClient)  {

    suspend fun getStripeClientSecret(products: ProductRequest) : String? =  client.request("payment/Stripe/getClientSecret"){
        method = HttpMethod.Post
        headers {
            append("Content-Type", "application/json")
        }
        setBody(products)

    }.body()

    suspend fun getStripeCredentials(): StripeCredentials? =  client.request("payment/Stripe/credentials"){
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }.body()

}