package com.fov.payment.events

import com.stripe.android.view.CardInputWidget


sealed class PayEvent {
    object DismissErrorDialog : PayEvent()
    data class LoadStripeWidget(val cardInputWidget: CardInputWidget) : PayEvent()
    data class LoadStripePaymentMethod(val method: (CardInputWidget, String,String, String) -> Unit) : PayEvent()
    data class LoadClientStripeSecret(val secret: String): PayEvent()
    data class LoadPublishableStripeKey(val key: String): PayEvent()
    data class LoadStripeAccountId(val id: String): PayEvent()
}