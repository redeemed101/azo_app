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
import androidx.compose.material.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.ColorFilter
import androidx.paging.compose.collectAsLazyPagingItems
import com.fov.common_ui.states.CommonState
import com.fov.main.ui.sermons.audio.general.MusicGeneralScreen
import com.fov.navigation.BackPageData
import com.fov.sermons.R
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
                val gridItems = getListOfGridItems(musicState,musicEvents,events)
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
private fun getListOfGridItems(
    musicState: MusicState,
    musicEvents : (event: MusicEvent) -> Unit,
    events : (event : CommonEvent) -> Unit
) : List<MusicHomeGridItem>{
    val list = mutableListOf<MusicHomeGridItem>()
    if(musicState.genres.isNotEmpty())
        musicState.genres.forEach { genre ->
            list.add(MusicHomeGridItem(
                genre.name,
                R.drawable.ic_genre_music
            ){

                events(CommonEvent.ChangeHasDeepScreen(true, genre.name))
                events(CommonEvent.ChangeBackPageData(BackPageData(true,"Genres")))
                musicEvents(MusicEvent.GenreSelected(genre))
                musicEvents(MusicEvent.GoToGenre)

            })
        }
    return list

}

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
            .background(MaterialTheme.colors.primary.copy(alpha = 1f))
            .clickable {
                item.action()
            }
            //.background(White009)
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            tint =  MaterialTheme.colors.primaryVariant,
            modifier = Modifier.padding(10.dp),
            contentDescription = null
        )

        Text(item.name,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.caption.copy(
                MaterialTheme.colors.primaryVariant,
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