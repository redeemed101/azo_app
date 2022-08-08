package com.fov.navigation

import com.fov.navigation.AuthenticationDirections.Default
import kotlinx.coroutines.flow.MutableStateFlow

class NavigationManager {

    var commands = MutableStateFlow(Default)

    fun navigate(
        directions : NavigationCommand
    ){

        commands.value = directions
    }
}