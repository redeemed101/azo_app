package com.fov.payment.models

data class PaymentMethod(val name: String, val description: String, val icon : Int, val type : String)

enum class PaymentType {
    CARD
}
