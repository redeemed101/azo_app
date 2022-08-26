package com.fov.main.ui.sermons.library.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.sermons.viewModels.SermonViewModel
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.bottomTabHeight
import com.fov.common_ui.theme.commonPadding
import com.fov.main.ui.sermons.audio.general.MusicGeneralScreen
import com.fov.main.ui.sermons.library.general.LibraryGeneralScreen
import com.fov.main.ui.sermons.library.sections.DownloadedAlbumsTab
import com.fov.main.ui.sermons.library.sections.DownloadedSongsTab
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.events.StoredMusicEvent
import com.fov.sermons.states.MusicState
import com.fov.sermons.states.StoredMusicState
import com.fov.sermons.viewModels.StoredSermonViewModel


@ExperimentalAnimationApi
@Composable
fun LibraryHomeScreen(
    musicViewModel : SermonViewModel,
    storedSermonViewModel: StoredSermonViewModel,
    commonViewModel: CommonViewModel
){
    val commonState by commonViewModel.uiState.collectAsState()
    val musicState by musicViewModel.uiState.collectAsState()
    val storedState by storedSermonViewModel.uiState.collectAsState()
    LibraryHome(
        commonState = commonState,
        events = commonViewModel::handleCommonEvent,
        musicState = musicState,
        musicEvents = musicViewModel::handleMusicEvent,
        storedMusicState = storedState,
        storedMusicEvents = storedSermonViewModel::handleMusicEvent

    )


}


@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun LibraryHome(
    commonState : CommonState,
    events: (event: CommonEvent) -> Unit,
    musicState: MusicState,
    musicEvents: (event: MusicEvent) -> Unit,
    storedMusicState: StoredMusicState,
    storedMusicEvents : (event: StoredMusicEvent) -> Unit

    ){
    val scope = rememberCoroutineScope()
    LaunchedEffect(commonState.currentTab) {

        storedMusicEvents(StoredMusicEvent.LoadDownloadedSongs)
        storedMusicEvents(StoredMusicEvent.LoadDownloadedAlbums)
    }
    LibraryGeneralScreen(
        commonState = commonState,
        events = events,
        musicState = musicState,
        musicEvents = musicEvents,
        swipeToRefreshAction = {
            musicEvents(MusicEvent.LoadHome)
        }
    ) {
        var selectedTabState by remember { mutableStateOf(DownloadedMusicTabs.Songs) }

        val backgroundColor = MaterialTheme.colors.onSurface
        BoxWithConstraints() {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            Column{
                TabRow(
                    selectedTabIndex = selectedTabState.ordinal,
                    backgroundColor = backgroundColor,
                    modifier = Modifier
                        //.padding(vertical = 24.dp)
                        .height(40.dp),
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            color = MaterialTheme.colors.surface,
                            height = 4.dp,
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[selectedTabState.ordinal])
                        )
                    }
                ) {
                    Tab(
                        modifier = Modifier.padding(bottom = 10.dp),
                        selected = DownloadedMusicTabs.Songs == selectedTabState,
                        onClick = { selectedTabState = DownloadedMusicTabs.Songs }
                    )
                    {
                        Column {

                            Text(
                                "Songs",
                                style = MaterialTheme.typography.h5.copy(
                                    MaterialTheme.colors.surface,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp

                                )
                            )
                        }
                    }
                    Tab(
                        modifier = Modifier.padding(bottom = 10.dp),
                        selected = DownloadedMusicTabs.Albums == selectedTabState,
                        onClick = { selectedTabState = DownloadedMusicTabs.Albums}
                    )
                    {
                        Column {

                            Text(
                                "Albums",
                                style = MaterialTheme.typography.h5.copy(
                                    MaterialTheme.colors.surface,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp

                                )
                            )
                        }
                    }
                }
                when (selectedTabState) {
                    DownloadedMusicTabs.Songs  ->  DownloadedSongsTab(
                        storedMusicState = storedMusicState,
                        storedMusicEvents = storedMusicEvents,
                        musicState = musicState,
                        musicEvents = musicEvents,
                        commonState = commonState, events = events)
                    DownloadedMusicTabs.Albums -> DownloadedAlbumsTab(
                        storedMusicState = storedMusicState,
                        storedMusicEvents = storedMusicEvents,
                        musicState = musicState,
                        musicEvents = musicEvents,
                        commonState = commonState, events = events)
                }

                Spacer(modifier = Modifier.height(commonPadding))
                Spacer(modifier = Modifier.height(bottomTabHeight))

            }
        }

    }

}

private enum class DownloadedMusicTabs {
    Songs,
    Albums

}
