package com.fov.main.ui.sermons.audio.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.bottomTabHeight
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.main.ui.sermons.audio.general.MusicGeneralScreen
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.states.MusicState
import com.fov.sermons.ui.albums.Albums
import com.fov.sermons.ui.music.MusicSection
import com.fov.sermons.viewModels.SermonViewModel


@ExperimentalAnimationApi
@Composable
fun GenreScreen(
    musicViewModel : SermonViewModel,
    commonViewModel: CommonViewModel,
){
    val commonState by commonViewModel.uiState.collectAsState()
    val musicState by musicViewModel.uiState.collectAsState()
    Genre(
        commonState = commonState,
        events = commonViewModel::handleCommonEvent,
        musicState = musicState,
        musicEvents = musicViewModel::handleMusicEvent,
    )
}

@ExperimentalAnimationApi
@Composable
private fun Genre(
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
            if(musicState.selectedGenre != null) {
                musicEvents(MusicEvent.GenreSelected(musicState.selectedGenre!!))
            }
        },
        backHandler = {
            events(CommonEvent.ChangeHasDeepScreen(true, "Genres"))
        }
    ) {
        BoxWithConstraints() {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {

                MusicSection(
                    "New Singles", false,
                    musicState.genreData!!.songs,
                    musicEvents
                ) {

                }
                Spacer(modifier = Modifier.height(commonPadding))
                Spacer(modifier = Modifier.height(commonPadding))
                Albums(
                    "Top Albums",
                    musicState.genreData!!.albums,
                    musicEvents
                ) {  }


                Spacer(modifier = Modifier.height(commonPadding))
                Spacer(modifier = Modifier.height(bottomTabHeight))
            }
        }
    }
}