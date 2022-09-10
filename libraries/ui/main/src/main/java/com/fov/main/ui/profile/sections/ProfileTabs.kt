package com.fov.main.ui.profile.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fov.authentication.events.UsersEvent
import com.fov.authentication.states.UsersState
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.states.CommonState
import com.fov.main.ui.sermons.library.sections.DownloadedSongsTab
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.events.StoredMusicEvent
import com.fov.sermons.states.MusicState
import com.fov.sermons.states.StoredMusicState

@Composable
fun profileTabs(
    commonState: CommonState,
    commonEvents : (CommonEvent) -> Unit,
    storedMusicState: StoredMusicState,
    storedMusicEvents : (StoredMusicEvent) -> Unit,
    musicState: MusicState,
    musicEvents: (MusicEvent) -> Unit,
){

    var selectedTabState by remember { mutableStateOf(Tabs.Music) }
    var backgroundColor = MaterialTheme.colors.surface

    Column {
        TabRow(
            selectedTabIndex = selectedTabState.ordinal,
            backgroundColor = backgroundColor,
            modifier = Modifier
                .padding(vertical = 24.dp)
                .height(40.dp),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = MaterialTheme.colors.primary,
                    height = 4.dp,
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabState.ordinal])
                )
            }
        ) {
            Tab(
                modifier = Modifier.padding(bottom = 10.dp),
                selected = Tabs.Music == selectedTabState,
                onClick = { selectedTabState = Tabs.Music }
            )
            {
                Text(
                    "Saved Sermons",
                    color = MaterialTheme.colors.onSurface,

                    )
            }

            /*Tab(
                modifier = Modifier.padding(bottom = 10.dp),
                selected = Tabs.Activity == selectedTabState,
                onClick = {
                    selectedTabState = Tabs.Activity

                }
            )
            {
                Text(
                    "Activity",
                    color = MaterialTheme.colors.onSurface,

                    )
            }*/



        }
        when (selectedTabState) {
            Tabs.Music -> DownloadedSongsTab(
                storedMusicState,
                storedMusicEvents = storedMusicEvents,
                musicState,
                musicEvents,
                commonState,
                commonEvents
            );
            /*Tabs.Activity ->{
                if(usersState.userModel!!.isSubscribed)
                    ActivityTab(
                        playlistState.userPlaylists,
                        events
                    )

            }*/

        }
    }

}

enum class Tabs {
    Music,
    //Activity,

}
