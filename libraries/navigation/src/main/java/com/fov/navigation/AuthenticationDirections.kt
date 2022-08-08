package com.fov.navigation

import androidx.navigation.NamedNavArgument

object AuthenticationDirections {
    val Default = object : NavigationCommand{
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = ""
    }
    val onboarding = object : NavigationCommand{
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = RouteConstants.ONBOARDING
    }
    val newFollow = object : NavigationCommand{
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = RouteConstants.NEW_FOLLOW
    }
    val login = object : NavigationCommand{
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = RouteConstants.LOGIN
    }
    val verifyAccount = object : NavigationCommand{
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = RouteConstants.VERIFY_ACCOUNT
    }
    val registration = object : NavigationCommand{
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = RouteConstants.REGISTRATION
    }
    val mainTab = object : NavigationCommand{
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = RouteConstants.MAINTAB
    }
    val forgotPassword = object : NavigationCommand{
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = RouteConstants.FORGOTPASSWORD
    }
    val resetPassword = object : NavigationCommand{
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = RouteConstants.RESETPASSWORD
    }
}