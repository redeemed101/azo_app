package com.fov.main.ui.sermons.video.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.fov.common_ui.R
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.extensions.itemsCustom
import com.fov.common_ui.extensions.itemsCustomized
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.bottomTabHeight
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.theme.padding10
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.main.ui.sermons.video.general.VideoGeneralScreen
import com.fov.navigation.BackPageData
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.mock.data.videos.VIDEOS
import com.fov.sermons.states.MusicState
import com.fov.sermons.viewModels.SermonViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun VideoHomeScreen(
    musicViewModel : SermonViewModel,
    commonViewModel: CommonViewModel,
    playVideo : (id : String) -> Unit
){
    val commonState by commonViewModel.uiState.collectAsState()
    val musicState by musicViewModel.uiState.collectAsState()
    videos(commonState = commonState,
        events = commonViewModel::handleCommonEvent,
        musicState = musicState,
        musicEvents = musicViewModel::handleMusicEvent,
        playVideo
    )

}
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun videos(
    commonState : CommonState,
    events: (event: CommonEvent) -> Unit,
    musicState: MusicState,
    musicEvents: (event: MusicEvent) -> Unit,
    playVideo : (id : String) -> Unit
){
   VideoGeneralScreen(
        commonState = commonState,
        events = events,
        musicState = musicState,
        musicEvents = musicEvents,
        swipeToRefreshAction = {
            musicEvents(MusicEvent.LoadHome)
        }
    ) {

       LaunchedEffect(commonState.hasDeepScreen) {
           musicEvents(MusicEvent.LoadGenres)
           events(CommonEvent.ChangeBackPageData(BackPageData()))

       }
       BoxWithConstraints() {
           val screenWidth = maxWidth
           val screenHeight = maxHeight


       }
       val videos = flowOf(PagingData.from(VIDEOS)).collectAsLazyPagingItems()
       LazyVerticalGrid(
           columns = GridCells.Fixed(3),
           modifier = Modifier
               .padding(horizontal = padding10, vertical = commonPadding),
           ) {
           itemsCustom(videos){ video ->
               Box(
                   modifier = Modifier
                       .padding(horizontal = 10.dp)
                       .clickable {
                           playVideo(video!!.videoId)
                       }

               ) {
                   Column(
                       modifier = Modifier.zIndex(-1f)
                   ) {
                       Image(
                           painter = rememberAsyncImagePainter(
                               ImageRequest.Builder(LocalContext.current)
                                   .data(data = video!!.artwork).apply(
                                   block = fun ImageRequest.Builder.() {
                                       crossfade(true)
                                       fallback(R.drawable.image_placeholder)
                                       placeholder(R.drawable.image_placeholder)
                                   }).build()
                           ),
                           contentDescription = null,
                           modifier = Modifier
                               .size(200.dp)
                               .clip(MaterialTheme.shapes.medium)

                       )
                       Text(

                           video!!.videoName,
                           modifier = Modifier.padding(top = 10.dp),
                           textAlign = TextAlign.Start,
                           style = MaterialTheme.typography.h5.copy(
                               MaterialTheme.colors.onSurface,
                               fontWeight = FontWeight.Bold,
                               fontSize = 14.sp

                           ),
                       )
                   }
                   Icon(
                       painterResource(com.fov.sermons.R.drawable.ic_play),
                       "",
                       tint = MaterialTheme.colors.onPrimary,
                       modifier = Modifier
                           .align(Alignment.Center)
                           .zIndex(1f)
                           .size(40.dp)
                           .clickable {
                       }
                   )

               }

           }
       }
       Spacer(modifier = Modifier.height(commonPadding))
       Spacer(modifier = Modifier.height(bottomTabHeight))
    }
}