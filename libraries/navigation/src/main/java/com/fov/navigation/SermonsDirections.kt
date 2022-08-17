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
    val charts = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.CHARTS
        })
        override val destination = RouteConstants.CHARTS
    }
    val moods = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.MOODS
        })
        override val destination = RouteConstants.MOODS
    }
    val mood = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.MOOD
        })
        override val destination = RouteConstants.MOOD
    }
    val playlists = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.PLAYLISTS
        })
        override val destination = RouteConstants.PLAYLISTS
    }
    val song = object : NavigationCommand{

        override val arguments = listOf(navArgument(Constants.MAINTAB) {
            defaultValue = RouteConstants.SERMON
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