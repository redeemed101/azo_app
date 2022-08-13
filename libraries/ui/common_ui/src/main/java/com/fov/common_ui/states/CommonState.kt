package com.fov.common_ui.states


import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.paging.PagingData
import com.fov.common_ui.models.DownloadData
import com.fov.common_ui.theme.White009
import com.fov.domain.database.models.User
import com.fov.navigation.BackPageData
import com.fov.navigation.Screen
import com.fov.common_ui.utils.BottomSheetOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.io.File

class CommonState(
    val loading: Boolean = false,
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
    val hasSearchResult : Boolean = false,
    val errorMessage : String? = null,
    val imageCapture: ImageCapture? = null,
    val savingPhoto : Boolean = false,
    val cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    val preview: Preview? = null,
    val previewUri : Uri? = null,
    val filesPaging : Flow<PagingData<File>> = flowOf(PagingData.from(emptyList())),
    val isEmailValid  :  Boolean  =  false,
    val showMoreOptions : Boolean = false,
    val backPageData: BackPageData = BackPageData(),
    val showSearchOption : Boolean = false,
    val showSearchBar : Boolean = false,
    val topBarColor : Color = White009,
    val bottomSheetHeader : @Composable ColumnScope.() -> Unit = {},
    val bottomSheetAction :  () -> Unit = {},
    val showAddToPlaylist: Boolean = false,
    val downloadData : DownloadData =  DownloadData(),
    val showAddPlaylist : Boolean = false,
    val userId : String =  ""

) {

    companion object {
        fun initialise(): CommonState = CommonState()
    }
    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()
    class Builder(private val state: CommonState) {
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
        var hasSearchResult = state.hasSearchResult
        var  errorMessage = state.errorMessage
        var imageCapture = state.imageCapture
        var savingPhoto = state.savingPhoto
        var cameraSelector = state.cameraSelector
        var preview = state.preview
        var previewUri = state.previewUri
        var filesPaging = state.filesPaging
        var isEmailValid  =  state.isEmailValid
        var showMoreOptions = state.showMoreOptions
        var backPageData = state.backPageData
        var showSearchOption = state.showSearchOption
        var showSearchBar = state.showSearchBar
        var topBarColor = state.topBarColor
        var bottomSheetHeader = state.bottomSheetHeader
        var bottomSheetAction = state.bottomSheetAction
        var showAddToPlaylist = state.showAddToPlaylist
        var downloadData  = state.downloadData
        var showAddPlaylist = state.showAddPlaylist
        var userId =  state.userId

        fun build(): CommonState {
            return CommonState(
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
                hasSearchResult,
                errorMessage,
                imageCapture,
                savingPhoto,
                cameraSelector,
                preview,
                previewUri,
                filesPaging,
                isEmailValid,
                showMoreOptions,
                backPageData,
                showSearchOption,
                showSearchBar,
                topBarColor,
                bottomSheetHeader,
                bottomSheetAction,
                showAddToPlaylist,
                downloadData,
                showAddPlaylist,
                userId
            )
        }
    }

}