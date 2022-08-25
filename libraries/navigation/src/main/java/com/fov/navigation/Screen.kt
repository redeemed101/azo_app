package com.fov.navigation

import androidx.annotation.StringRes

sealed class Screen(val route: NavigationCommand, @StringRes val resourceId: Int? = null, val iconResourceId : Int?=null ) {
    //tab screens
    object Home : Screen(HomeDirections.home, R.string.home, R.drawable.ic_home)
    object Music : Screen(SermonsDirections.tab, R.string.sermons, R.drawable.ic_music)
    object Video : Screen(VideoDirections.tab, R.string.videos, R.drawable.ic_watch_video)
    object Library : Screen(SermonsDirections.downloaded_tab, R.string.library, R.drawable.ic_music_library)
    object Registration : Screen(AuthenticationDirections.registration)
    object Onboarding : Screen(AuthenticationDirections.registration)

    object Notifications : Screen(HomeDirections.notifications)
    object Search : Screen(HomeDirections.search)
    //auth screens
    object Login : Screen(AuthenticationDirections.login)
    object ForgotPassword : Screen(AuthenticationDirections.forgotPassword)
    object ResetPassword : Screen(AuthenticationDirections.resetPassword)
    object ChangePassword : Screen(HomeDirections.changePassword)


}

val tabItems = listOf(
    Screen.Home,
    Screen.Music,
    Screen.Video,
    Screen.Library
)