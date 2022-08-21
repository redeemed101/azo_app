package com.fov.navigation

import androidx.navigation.NamedNavArgument

object GeneralDirections {
    val Default = object : NavigationCommand{
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = ""
    }
    val back = object : NavigationCommand{
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "BACK"
    }
}