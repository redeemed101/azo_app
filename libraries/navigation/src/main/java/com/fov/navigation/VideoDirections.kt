package com.fov.navigation

import androidx.navigation.navArgument
import com.fov.core.utils.constants.Constants

object VideoDirections {
    val tab = object : NavigationCommand{
        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.VIDEOS
        })
        override val destination = RouteConstants.VIDEOS
    }
}