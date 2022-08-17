package com.fov.main.ui.home


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.fidarrappcompose.utils.helpers.setUpBottomSheet
import com.example.fidarrappcompose.utils.helpers.setUpSnackBar
import com.example.fidarrappcompose.utils.helpers.setUpToast
import com.fov.main.ui.home.topBar.BuildTopBar
import com.fov.authentication.viewModels.UsersViewModel
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.ui.composers.general.bottomSheetContent
import com.fov.common_ui.ui.composers.sections.DarkOverlay
import com.fov.common_ui.utils.constants.Constants
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.main.ui.home.bottomBar.BuildBottomBar
import com.fov.main.ui.home.navigation.NavigationHost
import com.fov.main.viewModels.MainViewModel
import com.fov.navigation.NavigationCommand
import com.fov.navigation.Screen
import com.fov.sermons.viewModels.SermonViewModel
import com.fov.sermons.viewModels.StoredSermonViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalFoundationApi
@InternalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi

@Composable
fun MainTabUI(
    navController : NavHostController,
    mainViewModel: MainViewModel,
    musicViewModel: SermonViewModel,
    usersViewModel: UsersViewModel,
    commonViewModel: CommonViewModel,
    storedMusicViewModel: StoredSermonViewModel,
    navigate : (route : NavigationCommand) -> Unit
){
    //val state by mainViewModel.uiState.collectAsState()
    val userState by usersViewModel.uiState.collectAsState()
    val state by commonViewModel.uiState.collectAsState()
    MainHome(
        navController,
        mainViewModel,
        musicViewModel,
        usersViewModel,
        commonViewModel,
        storedMusicViewModel,
        navigate
    )
}
@ExperimentalFoundationApi
@InternalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun MainHome(
    navController : NavHostController,
    mainViewModel: MainViewModel,
    musicViewModel: SermonViewModel,
    usersViewModel: UsersViewModel,
    commonViewModel: CommonViewModel,
    storedMusicViewModel: StoredSermonViewModel,
    navigate : (route : NavigationCommand) -> Unit
){

    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val userState by usersViewModel.uiState.collectAsState()
    val musicState by musicViewModel.uiState.collectAsState()
    val commonState by commonViewModel.uiState.collectAsState()
    val events = commonViewModel::handleCommonEvent
    val userEvents = usersViewModel::handleUsersEvent
    val musicEvents = musicViewModel::handleMusicEvent
    val storedMusicEvents = storedMusicViewModel::handleMusicEvent
    val isSongDownloaded  = musicState.selectedSong?.songId?.let {
        storedMusicViewModel.isSongDownloadedAsync(it)
        .collectAsState( initial = false ).value
    } ?: false
    val isAlbumDownloaded  = musicState.selectedAlbum?.albumId?.let {
        storedMusicViewModel.isAlbumDownloadedAsync(it)
            .collectAsState( initial = false ).value
    } ?: false



    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentTab = navBackStackEntry?.arguments?.getString(Constants.MAINTAB)
        ?: Screen.Home.route.destination


        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberBottomSheetState(
                initialValue = BottomSheetValue.Collapsed
            )
        )

         BottomSheetScaffold(
            sheetContent = {

                  bottomSheetContent(
                      bottomSheetOptions = commonState.bottomSheetOptions,
                      header = commonState.bottomSheetHeader
                  )

            },
            sheetBackgroundColor = Color.Transparent,
            drawerBackgroundColor = Color.Transparent,
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = 0.dp,
            sheetShape = RectangleShape
        ) {

          Box() {


            Scaffold(

                scaffoldState = scaffoldState,

                topBar = BuildTopBar(
                    scope = scope,
                    context = context,
                    bottomSheetScaffoldState =bottomSheetScaffoldState ,
                    lifecycleOwner = lifecycleOwner,
                    commonState = commonState,
                    events = events,
                    userState = userState,
                    userEvents = userEvents,
                    musicState = musicState,
                    musicEvents = musicEvents,
                    storedMusicEvents = storedMusicEvents,
                    isSongDownloaded = isSongDownloaded
                ),
                bottomBar = BuildBottomBar(
                    commonState = commonState,
                    events = events,
                    musicEvents = musicEvents,
                    navigate = navigate,
                    currentTab = currentTab
                )

            ) {
                 it.calculateBottomPadding()
                 NavigationHost(
                     navController,
                     mainViewModel,
                     musicViewModel,
                     usersViewModel,
                     commonViewModel,
                     storedMusicViewModel,
                 )

            }

              SnackbarHost(
                  hostState = snackBarHostState,
                  modifier = Modifier
                      .background(color = MaterialTheme.colors.secondary)
              )

              setUpToast(commonState.toast,context)
              setUpSnackBar(commonState.errorMessage,snackBarHostState,scope)

              setUpBottomSheet(bottomSheetScaffoldState = bottomSheetScaffoldState, scope = scope)




              if(commonState.isShowingStory) {
                  BoxWithConstraints {

                      Box{
                          DarkOverlay {
                              events(CommonEvent.ToggleShowStory(false))
                          }


                      }

                  }


              }
        }
      }
}










