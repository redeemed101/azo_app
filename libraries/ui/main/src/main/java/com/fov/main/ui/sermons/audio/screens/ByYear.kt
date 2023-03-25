package com.fov.main.ui.sermons.audio.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.extensions.itemsCustom
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.bottomTabHeight
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.theme.padding10
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.main.ui.sermons.audio.general.MusicGeneralScreen
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.states.MusicState
import com.fov.sermons.ui.albums.Albums
import com.fov.sermons.ui.music.MusicItem
import com.fov.sermons.ui.music.MusicSection
import com.fov.sermons.viewModels.SermonViewModel


@ExperimentalAnimationApi
@Composable
fun ByYearScreen(
    musicViewModel : SermonViewModel,
    commonViewModel: CommonViewModel,
){
    val commonState by commonViewModel.uiState.collectAsState()
    val musicState by musicViewModel.uiState.collectAsState()
    ByYear(
        commonState = commonState,
        events = commonViewModel::handleCommonEvent,
        musicState = musicState,
        musicEvents = musicViewModel::handleMusicEvent,
    )
}

@ExperimentalAnimationApi
@Composable
private fun ByYear(
    commonState : CommonState,
    events: (event: CommonEvent) -> Unit,
    musicState: MusicState,
    musicEvents: (event: MusicEvent) -> Unit,
){
    MusicGeneralScreen(
        commonState = commonState,
        events = events,
        musicState = musicState,
        musicEvents = musicEvents,
        swipeToRefreshAction = {
            if(musicState.selectedYear != null) {
                musicEvents(MusicEvent.YearSelected(musicState.selectedYear!!))
            }
        },
        backHandler = {
            //events(CommonEvent.ChangeHasDeepScreen(true, "Genres"))
        }
    ) {
        BoxWithConstraints() {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            val scrollState = rememberScrollState()



                val pageItems = musicState.yearSongs.collectAsLazyPagingItems()
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 100.dp),
                    modifier = Modifier.padding(padding10),

                    ) {
                    itemsCustom(pageItems) { song ->
                        MusicItem(song = song!!){
                            {}
                            musicEvents(MusicEvent.SaveRecentSearch(song!!))
                            musicEvents(MusicEvent.SongSelected(song!!))
                            musicEvents(MusicEvent.ChangeShowingSong(true))

                        }
                    }
                }



        }
    }
}