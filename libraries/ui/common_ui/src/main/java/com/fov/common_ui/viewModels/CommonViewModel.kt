package com.fov.common_ui.viewModels

import android.app.Application
import android.media.MediaScannerConnection
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.lifecycle.*
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.utils.helpers.Utilities
import com.fov.core.di.Preferences
import com.fov.domain.database.daos.UserDao
import com.fov.domain.database.models.User
import com.fov.navigation.GeneralDirections
import com.fov.navigation.HomeDirections
import com.fov.navigation.NavigationManager
import com.fov.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject
import androidx.camera.core.ImageCapture.Metadata
import androidx.compose.material.MaterialTheme
import androidx.core.net.toFile
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fov.common_ui.mock.data.NEWS
import com.fov.common_ui.models.NewsModel
import com.fov.common_ui.pagination.FilesSource
import com.fov.domain.utils.constants.QueryConstants
import kotlinx.coroutines.flow.*


@HiltViewModel
class CommonViewModel @Inject constructor(
    application: Application,
    private val navigationManager: NavigationManager,
    private val sharedPreferences: Preferences,
    private val userDao: UserDao
) :  AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(CommonState())
    val uiState: StateFlow<CommonState> = _uiState

    val users: LiveData<List<User>> =  userDao.getUsers().asLiveData()

    private var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    val permissionsInitiallyRequestedState = MutableStateFlow(false)

    init{
        _uiState.value.build {
            //topBarColor = MaterialTheme.colors.onSurface
            //topBarTintColor = MaterialTheme.colors.surface
        }
    }



    private fun replaceImageCapture() {
        _uiState.value.build {
            imageCapture = newImageCapture()
        }
    }

    private fun newImageCapture() = ImageCapture.Builder().build()

    private fun takePhotoAsync(callback : (Uri) -> Unit) : Uri? {


            val photoFile = File(
                Utilities.getOutputDirectory(getApplication<Application>().applicationContext),
                SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
                    .format(System.currentTimeMillis()) + ".jpg"
            )
            var savedUri = Uri.fromFile(photoFile)
            val metadata = Metadata().apply {
                isReversedHorizontal = _uiState.value.cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA
            }
            val outputOptions = ImageCapture.OutputFileOptions
                .Builder(photoFile)
                .setMetadata(metadata)
                .build()

            _uiState.value.imageCapture?.takePicture(
                outputOptions,
                cameraExecutor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        //savedUri = outputFileResults.savedUri

                        val mimeType = MimeTypeMap.getSingleton()
                            .getMimeTypeFromExtension(savedUri.toFile().extension)

                        MediaScannerConnection.scanFile(
                            getApplication<Application?>().applicationContext,
                            arrayOf(savedUri.toFile().absolutePath),
                            arrayOf(mimeType)
                        ){_, uri ->
                            Log.d("CAMERA","$uri")
                        }
                        _uiState.value.build { previewUri = savedUri }
                        callback(savedUri)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.e("CAMERA", exception.message!!)
                    }

                }
            )
            return savedUri
    }

    override fun onCleared() {
        cameraExecutor.shutdown()
        super.onCleared()
    }

    init {

        viewModelScope.launch {
                 sharedPreferences.isVerified?.collectLatest { isVer ->
                     _uiState.value = uiState.value.build {
                         if(isVer != null)
                             isVerified = isVer
                     }


                 }

                 sharedPreferences.accessToken?.let { token ->
                     token.collectLatest { it ->
                         if(it != null && it != "") {

                             userDao.getUser().collect { usr ->
                                 _uiState.value = uiState.value.build {
                                     isAuthenticated = true
                                     user = usr
                                 }
                             }

                         }
                         else{
                             _uiState.value = uiState.value.build {
                                 isAuthenticated = false
                             }
                         }
                     }
               }
               sharedPreferences.encryptionKey.let { key ->
                   key.collectLatest { k ->
                       _uiState.value = uiState.value.build {

                       }
                   }

               }
           }
        _uiState.value = uiState.value.build {
            imageCapture = newImageCapture()
            preview = Preview.Builder()
                .build()

        }
    }
    fun handleCommonEvent(event : CommonEvent){
        _uiState.value = uiState.value.build {
            when (event) {
                CommonEvent.DismissErrorDialog -> {

                }
                is CommonEvent.ChangeUserId ->{
                    userId = event.userId
                }
                is CommonEvent.ChangeDownloadData -> {
                    downloadData = event.data
                }
                is CommonEvent.ChangeBottomSheetAction -> {
                    bottomSheetAction = event.action
                }
                is CommonEvent.ChangeShowAddToPlaylist -> {
                    showAddToPlaylist = event.show
                }
                is CommonEvent.ChangeShowAddPlaylist -> {
                    showAddPlaylist = event.show
                }
                is CommonEvent.ChangeBottomSheetHeader -> {
                    bottomSheetHeader = event.header
                }
                is CommonEvent.ChangeLoading -> {
                    loading = event.loading
                }
                 CommonEvent.DismissToast -> {
                     this.toast = null
                 }
                CommonEvent.DoSearch -> {
                    doSearch()
                }
                CommonEvent.NewPhoto -> {
                    replaceImageCapture()
                }
                CommonEvent.TakePhoto -> {

                        previewUri = takePhotoAsync {
                             previewUri = it
                        }


                }
                CommonEvent.LoadNews -> {
                    news = getNews()
                }

                is CommonEvent.ChangeShowMoreOptions -> {
                    showMoreOptions = event.show
                }
                is CommonEvent.ChangeShowSearchOption -> {
                    showSearchOption = event.show
                }
                CommonEvent.SwitchCamera -> {

                    cameraSelector = if(cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA)
                        CameraSelector.DEFAULT_BACK_CAMERA else CameraSelector.DEFAULT_FRONT_CAMERA
                }
                is CommonEvent.ChangeShowSearchBar -> {
                    showSearchBar = event.show
                }
                is  CommonEvent.ChangeImagePreview ->{
                    previewUri = event.previewUri
                }
                is CommonEvent.ChangeWebViewUrl -> {
                    webUrl = event.uri
                }
                CommonEvent.NavigateToWebView ->{
                    navigationManager.navigate(HomeDirections.webview)
                }
                is CommonEvent.HasSearched -> {
                    hasSearchResult = event.searched
                }
                is CommonEvent.SearchTextChanged -> {
                    search = event.search
                }
                is CommonEvent.ShowToast -> {
                    this.toast = event.message
                }
                is CommonEvent.ToggleShowStory -> {
                    this.isShowingStory = event.show
                }
                CommonEvent.OnRefresh -> {
                    this.isRefreshing = true
                }
                CommonEvent.OnEndRefresh -> {
                    this.isRefreshing = false
                }
                is CommonEvent.GetBottomSheetOptions -> {
                    bottomSheetOptions = event.options
                }
                CommonEvent.NotificationsViewed -> {
                    this.numNotifications = 0
                }
                is CommonEvent.ChangeHasDeepScreen -> {
                    this.hasDeepScreen = event.isDeepScreen
                    this.screenTitle = event.deepScreenTitle

                }
                is CommonEvent.ChangeHasTopBar -> {
                    this.hasTopBar = event.showTopBar
                }
                is CommonEvent.ChangeHasBottomBar -> {
                    this.hasBottomBar = event.showBottomBar

                }
                is CommonEvent.ChangeTab -> {
                    if(currentTab != event.tab)
                       this.currentTab = event.tab
                }
                is CommonEvent.LoadUser -> {
                    user = event.user
                }
                is CommonEvent.GetFiles -> {
                    filesPaging = getFiles(event.extension)

                }
                CommonEvent.NavigateToNotifications ->{
                    navigationManager.navigate(HomeDirections.notifications)
                }
                is CommonEvent.ChangeBackPageData -> {
                       backPageData = event.data
                }

                is CommonEvent.ChangeTopBarColor -> {
                    topBarColor = event.color
                }
                is CommonEvent.ChangeTopBarTintColor -> {
                    topBarTintColor = event.color
                }
                CommonEvent.NavigateToSearch -> {
                    navigationManager.navigate(Screen.Search.route)
                }
                CommonEvent.NavigateUp -> {
                    navigationManager.navigate(GeneralDirections.back)
                }
                else -> {

                }
            }
        }
    }
    private fun getNews(): Flow<PagingData<NewsModel>> {
       return flowOf(PagingData.from(NEWS))

    }
    private fun getFiles(extension : String) : Flow<PagingData<File>> {
        try{
            return Pager(PagingConfig(pageSize = QueryConstants.NUM_ROWS)) {
                FilesSource(Utilities.getOutputDirectory(
                    getApplication<Application>().applicationContext),
                    extension
                )
            }.flow

        }
        catch(ex : Exception) {

        }
        return flowOf(PagingData.from(emptyList()))
    }

    private fun doSearch() {
        viewModelScope.launch {
            val search = _uiState.value.search
        }
    }



}