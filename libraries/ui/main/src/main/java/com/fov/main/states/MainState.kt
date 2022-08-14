package com.fov.main.states


import android.location.Location
import androidx.paging.PagingData
import com.fov.common_ui.utils.BottomSheetOption
import com.fov.domain.database.models.User
import com.fov.navigation.Screen
import kotlinx.coroutines.flow.Flow

class MainState(
    val loading: Boolean = true,
    val isAuthenticated: Boolean = false,
    val isVerified: Boolean = false,
    val screenTitle:String = "",
    val toast:String? = null,
    val bottomSheetOptions : List<BottomSheetOption> = listOf(),
    val isShowingStory: Boolean = false,
    val isRefreshing :Boolean = false,
    val hasDeepScreen: Boolean = false,
    val hasBottomBar: Boolean = true,
    val hasTopBar : Boolean  = true,
    val currentTab : Screen  = Screen.Home,
    val numNotifications : Int = 15,
    val user : User? = null,
    val search : String = "",
    val location: Location? = null,


    ) {

    companion object {
        fun initialise(): MainState = MainState()
    }
    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()
    class Builder(private val state: MainState) {
        var loading = state.loading
        var isAuthenticated = state.isAuthenticated
        var screenTitle = state.screenTitle
        var toast = state.toast
        var bottomSheetOptions = state.bottomSheetOptions
        var isShowingStory = state.isShowingStory
        var isRefreshing  = state.isRefreshing
        var hasDeepScreen = state.hasDeepScreen
        var numNotifications = state.numNotifications
        var user = state.user
        var isVerified = state.isVerified
        var hasTopBar = state.hasTopBar
        var hasBottomBar = state.hasBottomBar
        var currentTab = state.currentTab
        var search = state.search
        var location  = state.location

        fun build(): MainState {
            return MainState(
                loading,
                isAuthenticated,
                isVerified,
                screenTitle,
                toast,
                bottomSheetOptions,
                isShowingStory,
                isRefreshing,
                hasDeepScreen,
                hasBottomBar,
                hasTopBar,
                currentTab,
                numNotifications,
                user,
                search,
                location,
            )
        }
    }

}