package com.fov.main.ui.sermons.audio.screens


import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.bottomTabHeight
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.theme.padding10
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.main.ui.sermons.audio.general.MusicGeneralScreen
import com.fov.navigation.BackPageData
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.states.MusicState
import com.fov.sermons.utils.helpers.GENRE_IMAGES
import com.fov.sermons.viewModels.SermonViewModel


@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun YearsScreen(
    musicViewModel : SermonViewModel,
    commonViewModel: CommonViewModel,
){
    val commonState by commonViewModel.uiState.collectAsState()
    val musicState by musicViewModel.uiState.collectAsState()

    Years(
        commonState = commonState,
        events = commonViewModel::handleCommonEvent,
        musicState = musicState,
        musicEvents = musicViewModel::handleMusicEvent,
    )


}


@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
private fun Years(
    commonState : CommonState,
    events: (event: CommonEvent) -> Unit,
    musicState: MusicState,
    musicEvents: (event: MusicEvent) -> Unit
){
    MusicGeneralScreen(
        commonState = commonState,
        events = events,
        musicState = musicState,
        musicEvents = musicEvents,
        swipeToRefreshAction = {
            musicEvents(MusicEvent.LoadYears)
        }
    ) {
        LaunchedEffect(commonState.hasDeepScreen) {
            musicEvents(MusicEvent.LoadYears)
            events(CommonEvent.ChangeBackPageData(BackPageData()))

        }

        BoxWithConstraints() {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            // val genreItems = musicState.genres.collectAsLazyPagingItems()

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(padding10),

                ) {
                items(musicState.years.size) { index  ->
                    val item = musicState.years[index]
                    Card(
                        backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .height(100.dp)
                        ,
                        elevation = 0.dp,
                    ) {

                        ByYearItem(item = ByYearGridItem(
                            name = item.toString(),
                            icon = GENRE_IMAGES.random()
                        ){
                            //go to individual genre
                            events(CommonEvent.ChangeHasDeepScreen(true, item!!.toString()))
                            events(CommonEvent.ChangeBackPageData(BackPageData(true,"Years")))
                            musicEvents(MusicEvent.YearSelected(item!!))
                            musicEvents(MusicEvent.GoToYear)
                        }
                        )



                    }
                }
                item{
                    Spacer(modifier = Modifier.height(commonPadding))
                    Spacer(modifier = Modifier.height(bottomTabHeight))
                }


            }

        }
    }

}

@Composable
private fun ByYearItem(
    item: ByYearGridItem
){
    Box(

        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                item.action()
            }

        // .padding(padding10)
    ) {
        Image(
            painter = painterResource(item.icon),
            "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxSize()
        )
        Text(item.name,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.caption.copy(
                MaterialTheme.colors.surface,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}
private data class  ByYearGridItem(
    val name : String,
    @DrawableRes val icon : Int,
    val action  : () -> Unit
)