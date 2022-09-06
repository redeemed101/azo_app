package com.fov.payment.data

import com.fov.payment.R
import com.fov.payment.models.PaymentMethod
import com.fov.payment.models.PaymentType

val METHODS = listOf(
    PaymentMethod("Debit Card", "Pay using your Debit or Credit Card", R.drawable.credit_card, PaymentType.CARD.name)
)