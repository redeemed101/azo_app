package com.fov.main.ui.home.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fov.authentication.events.UsersEvent
import com.fov.authentication.states.UsersState
import com.fov.authentication.viewModels.UsersViewModel
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.main.ui.home.HomeGeneralScreen
import com.fov.main.ui.sermons.audio.general.MusicHomePager
import com.fov.navigation.BackPageData
import com.fov.navigation.Screen
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.mock.data.songs.PAGER_IMAGES
import com.fov.sermons.states.MusicState
import com.fov.sermons.ui.music.MusicSection
import com.fov.sermons.viewModels.SermonViewModel
import com.fov.sermons.ui.albums.Albums

@Composable
fun Home(
    commonViewModel: CommonViewModel,
    musicViewModel: SermonViewModel,
    usersViewModel: UsersViewModel
) {
    val state by commonViewModel.uiState.collectAsState()
    val musicState by musicViewModel.uiState.collectAsState()
    val usersState by usersViewModel.uiState.collectAsState()
    homeScreen(
        commonState = state,
        events = commonViewModel::handleCommonEvent,
        musicState = musicState,
        musicEvents = musicViewModel::handleMusicEvent,
        usersState = usersState,
        usersEvents = usersViewModel::handleUsersEvent,
    )
}
@Composable
private fun homeScreen(
    commonState : CommonState,
    events: (event: CommonEvent) -> Unit,
    musicState: MusicState,
    musicEvents: (event: MusicEvent) -> Unit,
    usersState: UsersState,
    usersEvents: (event: UsersEvent) -> Unit
) {
    HomeGeneralScreen(
        commonState = commonState,
        events = events,
        swipeToRefreshAction = {
            events(CommonEvent.OnRefresh)
            musicEvents(MusicEvent.LoadTopSongs)
            musicEvents(MusicEvent.LoadTopAlbums)
            musicEvents(MusicEvent.LoadForYou)
            events(CommonEvent.OnEndRefresh)
        }
    ) {
        val scrollState = rememberScrollState(0)
        BoxWithConstraints() {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            Column(
                modifier = Modifier
                    //.statusBarsPadding()

                    .verticalScroll(
                        scrollState
                    )
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.3f)
                ) {
                    MusicHomePager(
                        PAGER_IMAGES
                    )


                    Spacer(
                        Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        MaterialTheme.colors.surface.copy(0.5f),
                                        MaterialTheme.colors.surface
                                    )
                                )
                            )
                            .align(Alignment.BottomCenter)
                    )
                }

                if (musicState.forYouPaged != null) {
                    MusicSection(
                        "For You", false, musicState.forYouPaged!!,
                        musicEvents,
                        navigateTabAction = {/*events(CommonEvent.ChangeTab(Screen.Music))*/ }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (musicState.topSongsPaged != null) {
                    /*Singles("Top Singles",
                    musicState.topSongsPaged!!,
                    musicEvents
                )*/
                    MusicSection(
                        "Top Singles",
                        false,
                        musicState.topSongsPaged!!,
                        musicEvents,
                        navigateTabAction = { events(CommonEvent.ChangeTab(Screen.Music)) }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                if (musicState.topAlbumsPaged != null) {

                    Albums(
                        "Top Albums", musicState.topAlbumsPaged!!,
                        musicEvents,
                        navigateTabAction = {
                        /*events(CommonEvent.ChangeTab(Screen.Music))*/
                        }
                    )
                    Spacer(modifier = Modifier.height(commonPadding))
                    Spacer(modifier = Modifier.height(commonPadding))

                }

            }


        }
    }
}

