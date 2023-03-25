package com.fov.navigation



import androidx.navigation.navArgument
import com.fov.core.utils.constants.Constants

object SermonsDirections {
    val tab = object : NavigationCommand{
        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.SERMONS
        })
        override val destination = RouteConstants.SERMONS
    }
    val home = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.HOME
        })
        override val destination = RouteConstants.HOME
    }
    val genres = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.GENRES
        })
        override val destination = RouteConstants.GENRES
    }
    val genre = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.GENRE
        })
        override val destination = RouteConstants.GENRE
    }

    val years = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.YEARS
        })
        override val destination = RouteConstants.YEARS
    }
    val year = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.YEAR
        })
        override val destination = RouteConstants.YEAR
    }


    val downloaded_tab = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.DOWNLOADED
        })
        override val destination = RouteConstants.DOWNLOADED
    }


    val song = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.SERMONS
        })
        override val destination = RouteConstants.SERMON
    }
    val playSong = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.PLAY_SERMON
        })
        override val destination = RouteConstants.PLAY_SERMON
    }
    val album = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.ALBUM
        })
        override val destination = RouteConstants.ALBUM
    }
    val playlist = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.PLAYLIST
        })
        override val destination = RouteConstants.PLAYLIST
    }
}