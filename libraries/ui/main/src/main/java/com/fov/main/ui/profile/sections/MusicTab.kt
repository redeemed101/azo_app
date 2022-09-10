package com.fov.main.ui.profile.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fov.common_ui.theme.commonPadding
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.states.MusicState
import com.fov.sermons.states.StoredMusicState
import com.fov.sermons.ui.music.songColumn

@Composable
fun MusicTab(
    storedMusicState: StoredMusicState,
    musicState: MusicState,
    musicEvents: (MusicEvent) -> Unit,
){
    //val scrollState = rememberScrollState(0)
    Column(
        modifier = Modifier
            .padding(horizontal = commonPadding)
    ){
        songColumn(title = "Saved Sermons", storedMusicState.downloadedSongs,musicState, musicEvents)

        Spacer(modifier = Modifier.height(100.dp))
    }
}