package com.fov.navigation

import androidx.navigation.navArgument
import com.fov.core.utils.constants.Constants

object PaymentDirections {

    val options = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.HOME
        })
        override val destination = "payment_options"
    }
    val stripe = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.HOME
        })
        override val destination = "stripe_payment"
    }
}