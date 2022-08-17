package com.fov.main.ui.sermons.audio.screens

import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fov.common_ui.events.CommonEvent
import com.fov.main.ui.sermons.audio.general.MusicHomePager
import com.fov.common_ui.theme.*
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.sermons.ui.music.MusicSection
import com.fov.sermons.viewModels.SermonViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import com.fov.common_ui.states.CommonState
import com.fov.main.ui.sermons.audio.general.MusicGeneralScreen
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.mock.data.songs.PAGER_IMAGES
import com.fov.sermons.states.MusicState
import com.fov.sermons.ui.albums.Albums


@ExperimentalAnimationApi
@Composable
fun MusicHomeScreen(
    musicViewModel : SermonViewModel,
    commonViewModel: CommonViewModel
){
    val commonState by commonViewModel.uiState.collectAsState()
    val musicState by musicViewModel.uiState.collectAsState()
    MusicHome(
        commonState = commonState,
        events = commonViewModel::handleCommonEvent,
        musicState = musicState,
        musicEvents = musicViewModel::handleMusicEvent,
    )


}


@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun MusicHome(
    commonState : CommonState,
    events: (event: CommonEvent) -> Unit,
    musicState: MusicState,
    musicEvents: (event: MusicEvent) -> Unit,

    ){
    LaunchedEffect(commonState.hasDeepScreen) {

        musicEvents(MusicEvent.LoadHome)
    }
    MusicGeneralScreen(
        commonState = commonState,
        events = events,
        musicState = musicState,
        musicEvents = musicEvents,
        swipeToRefreshAction = {
            musicEvents(MusicEvent.LoadHome)
        }
    ) {
        BoxWithConstraints() {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            LazyColumn(

            ) {
                item {
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
                }
                item {
                    Spacer(modifier = Modifier.height(commonPadding))
                }
                /*item {
                MusicHomeGrid()
            }*/
                val gridItems = getListOfGridItems(musicEvents,events)
                items(gridItems.windowed(2, 2, true)) { sublist ->
                    Row(
                        Modifier
                            .padding(horizontal = padding10)
                            .fillMaxWidth()
                    ) {
                        sublist.forEach { item ->
                            Card(
                                backgroundColor = White009,
                                modifier = Modifier
                                    .padding(2.dp)

                                    .fillParentMaxWidth(.5f),
                                elevation = 8.dp,
                            ) {
                                GridItem(item = item)
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(commonPadding))
                }
                item {
                    MusicSection(
                        "New Singles", false, musicState.newSongs,
                        musicEvents
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(commonPadding))
                }
                item {
                    Albums(
                        "New Albums", musicState.newAlbums,
                        musicEvents
                    ) {  }
                }
                item {
                    Spacer(modifier = Modifier.height(commonPadding))
                }

                item {
                    Spacer(modifier = Modifier.height(commonPadding))
                }




            }
        }
    }

}
private fun getListOfGridItems(musicEvents : (event: MusicEvent) -> Unit, events : (event : CommonEvent) -> Unit) : List<MusicHomeGridItem>{
    return listOf(
        MusicHomeGridItem(
            "Genres",
            R.drawable.ic_genre_music
        ){

            musicEvents(MusicEvent.LoadGenres)
            musicEvents(MusicEvent.GoToGenres)
            events(CommonEvent.ChangeHasDeepScreen(true, "Genres"))


        },
        MusicHomeGridItem(
            "Moods",
            R.drawable.ic_music_mood
        ){
            events(CommonEvent.ChangeHasDeepScreen(true, "Moods"))
            musicEvents(MusicEvent.LoadMoods)
            musicEvents(MusicEvent.GoToMoods)
        },
        MusicHomeGridItem(
            "Charts",
            R.drawable.ic_music_chart
        ){
            events(CommonEvent.ChangeHasDeepScreen(true, "Charts"))
            musicEvents(MusicEvent.LoadCharts)
            musicEvents(MusicEvent.GoToCharts)
        },
        MusicHomeGridItem(
            "Playlists",
            R.drawable.ic_music_playlist
        ){
            events(CommonEvent.ChangeHasDeepScreen(true, "Playlists"))
            events(CommonEvent.ChangeShowSearchOption(true))
            musicEvents(MusicEvent.LoadPlaylists)
            musicEvents(MusicEvent.GoToPlaylists)
        },
    )
}
/*@OptIn(ExperimentalFoundationApi::class)
@Composable
fun  MusicHomeGrid(){
    val gridItems = getListOfGridItems()
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier.padding(horizontal = padding10),

    ) {
       items(gridItems.count()) { index  ->
           Card(
               backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
               modifier = Modifier
                   .padding(2.dp)
                   .fillMaxWidth(),
               elevation = 0.dp,
           ) {
               GridItem(item = gridItems[index])
           }
        }
    }
}*/
@Composable
private fun GridItem(
    item: MusicHomeGridItem
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(padding10)
            .height(40.dp)
            .clickable {
                item.action()
            }
            //.background(White009)
    ) {
        Image(
            painter = painterResource(item.icon),
            ""

        )
        Text(item.name,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.caption.copy(
                MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
private data class  MusicHomeGridItem(
    val name : String,
    @DrawableRes val icon : Int,
    val action  : () -> Unit
)