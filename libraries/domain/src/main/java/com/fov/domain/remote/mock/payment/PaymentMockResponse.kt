package com.fov.domain.remote.mock.payment

import com.fov.domain.models.payment.StripeCredentials
import com.google.gson.Gson

object PaymentStripeCredentialsMockResponse {
    operator fun invoke(): String {
        val response = StripeCredentials(
            publishableKey = "pk_test_51IiPFcFquib2y1bYKRvER0JyfuQ8Zt5bB7L5UadXtFMyHmcDIgrGe6pgYA6MzaCz4tW7Clgp2l6VjsdQE1ph8tlT00eUmFWUFN",
            stripeAccountId = "acct_1IiPFcFquib2y1bY"
        )
        return Gson().toJson(response)
    }
}
object PaymentStripeCustomerIdResponse{
    operator fun invoke(): String {
        return "pi_3LeHoaFquib2y1bY02ZhGUh5_secret_3DBfFMt1tYCSto4cC2ZGVJwB6"
    }
}