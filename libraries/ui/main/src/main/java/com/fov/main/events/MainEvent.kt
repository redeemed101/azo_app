package com.fov.main.events

import android.location.Location
import com.fov.common_ui.utils.BottomSheetOption
import com.fov.navigation.Screen

sealed class MainEvent {
    object DismissErrorDialog : MainEvent()
    data class ToggleShowStory(val show: Boolean) : MainEvent()
    object NavigateToProfile : MainEvent()
    object NavigateToNotifications : MainEvent()
    object NavigateToSearch : MainEvent()
    object NavigateUp : MainEvent()
    object OnRefresh : MainEvent()
    object OnEndRefresh : MainEvent()
    object DismissToast : MainEvent()
    data class ShowToast(val message : String) : MainEvent()
    data class SearchTextChanged(val search : String) : MainEvent()
    data class LoadLocation(val location: Location) : MainEvent()
    data class GetBottomSheetOptions(val options : List<BottomSheetOption>) : MainEvent()
    object NotificationsViewed : MainEvent()
    data class ChangeHasDeepScreen(val isDeepScreen : Boolean,val deepScreenTitle :String ) : MainEvent()
    data class ChangeHasTopBar(val showTopBar: Boolean) : MainEvent()
    data class ChangeHasBottomBar(val showBottomBar: Boolean) : MainEvent()
    data class ChangeTab(val tab: Screen) : MainEvent()
}