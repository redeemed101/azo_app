package com.fov.common_ui.events

import android.net.Uri
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.fov.common_ui.models.DownloadData
import com.fov.common_ui.utils.BottomSheetOption
import com.fov.domain.database.models.User
import com.fov.navigation.BackPageData
import com.fov.navigation.Screen


sealed class CommonEvent {
    object DismissErrorDialog : CommonEvent()
    data class ToggleShowStory(val show: Boolean) : CommonEvent()
    data class NavigateToProfile(val userId: String) : CommonEvent()
    data class GetFiles(val extension: String) : CommonEvent()
    data class ChangeBackPageData (val data : BackPageData) : CommonEvent()
    object NavigateToNotifications : CommonEvent()
    object NavigateToSearch : CommonEvent()
    object NavigateUp : CommonEvent()
    object OnRefresh : CommonEvent()
    object OnEndRefresh : CommonEvent()
    object DismissToast : CommonEvent()
    object DoSearch : CommonEvent()
    object TakePhoto : CommonEvent()
    object NewPhoto : CommonEvent()
    data class ChangeImagePreview(val previewUri: Uri?) : CommonEvent()
    data class ChangeShowMoreOptions(val show : Boolean) : CommonEvent()
    data class ChangeShowSearchOption(val show : Boolean) : CommonEvent()
    data class ChangeShowSearchBar(val show : Boolean) : CommonEvent()
    data class LoadUser(val user : User) : CommonEvent()
    data class HasSearched(val searched : Boolean) : CommonEvent()
    data class ShowToast(val message : String) : CommonEvent()
    data class SearchTextChanged(val search : String) : CommonEvent()
    data class GetBottomSheetOptions(val options : List<BottomSheetOption>) : CommonEvent()
    object NotificationsViewed : CommonEvent()
    object SwitchCamera : CommonEvent()
    data class ChangeHasDeepScreen(val isDeepScreen : Boolean,val deepScreenTitle :String ) : CommonEvent()
    data class ChangeHasTopBar(val showTopBar: Boolean) : CommonEvent()
    data class ChangeHasBottomBar(val showBottomBar: Boolean) : CommonEvent()
    data class ChangeTab(val tab: Screen) : CommonEvent()
    data class ChangeTopBarColor(val color: Color) : CommonEvent()
    data class ChangeTopBarTintColor(val color: Color) : CommonEvent()
    data class ChangeLoading(val loading: Boolean) : CommonEvent()
    data class ChangeBottomSheetHeader(val header: @Composable ColumnScope.() -> Unit) : CommonEvent()
    data class ChangeBottomSheetAction(val action : () -> Unit) : CommonEvent()
    data class ChangeShowAddToPlaylist(val show : Boolean) : CommonEvent()
    data class ChangeDownloadData(val data : DownloadData) : CommonEvent()
    data class ChangeShowAddPlaylist(val show : Boolean) : CommonEvent()
    data class ChangeUserId(val userId: String) : CommonEvent()
}