package com.fov.payment.states

import com.fov.payment.models.PaymentMethod
import com.stripe.android.view.CardInputWidget


class PayState(
    val loading: Boolean = false,
    val activationCode: String = "",
    val isPaymentValid: Boolean = false,
    val cardWidget: CardInputWidget? = null,
    val clientStripeSecret : String? = null,
    val stripePublishableKey: String? = null,
    val stripeAccountId: String? = null,
    val stripePaymentMethod: (CardInputWidget, String,String, String) -> Unit = {_,_, _,_ ->

    },
    val errorText : String? = null,
    val paymentMethods : List<PaymentMethod> = emptyList()
){
    companion object {
        fun initialise(): PayState = PayState()
    }
    fun build(block: PayState.Builder.() -> Unit) = PayState.Builder(this).apply(block).build()
    class Builder(private val state: PayState) {
        var loading = state.loading
        var activationCode = state.activationCode
        var isPaymentValid = state.isPaymentValid
        var cardInputWidget = state.cardWidget
        var stripePaymentMethod = state.stripePaymentMethod
        var clientStripeSecret = state.clientStripeSecret
        var stripePublishableKey = state.stripePublishableKey
        var stripeAccountId = state.stripeAccountId
        var errorText = state.errorText
        var paymentMethods = state.paymentMethods
        fun build(): PayState {
            return PayState(
                loading,
                activationCode,
                isPaymentValid,
                cardInputWidget,
                clientStripeSecret,
                stripePublishableKey,
                stripeAccountId,
                stripePaymentMethod,
                errorText,
                paymentMethods

            )
        }
    }
}