package com.fov.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument
import com.fov.core.utils.constants.Constants

object HomeDirections {
    val Default = object : NavigationCommand{
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = ""
    }
    val back = object : NavigationCommand{
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "BACK"
    }
    val home = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.HOME
        })
        override val destination = RouteConstants.HOME
    }
    val changePassword = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.HOME
        })
        override val destination = RouteConstants.CHANGEPASSWORD
    }
    val search = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.HOME
        })
        override val destination = RouteConstants.SEARCH
    }

    val notifications = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.HOME
        })
        override val destination = RouteConstants.NOTIFICATIONS
    }
}