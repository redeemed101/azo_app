package com.fov.main.ui.news.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.fov.common_ui.R
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.extensions.itemsCustomized
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.main.ui.news.general.NewsGeneralScreen
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.states.MusicState
import com.fov.sermons.viewModels.SermonViewModel
import com.fov.sermons.viewModels.StoredSermonViewModel

@Composable
fun NewsHome(
    commonViewModel: CommonViewModel,
    sermonViewModel: SermonViewModel
){
    val commonState by commonViewModel.uiState.collectAsState()
    val musicState by sermonViewModel.uiState.collectAsState()
    newsHome(
        commonState = commonState,
        events = commonViewModel::handleCommonEvent,
        musicState = musicState,
        musicEvents = sermonViewModel::handleMusicEvent
    )
}
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun newsHome(
    commonState : CommonState,
    events: (event: CommonEvent) -> Unit,
    musicState: MusicState,
    musicEvents: (event: MusicEvent) -> Unit,
){
    NewsGeneralScreen(commonState = commonState,
        events = events,
        musicState = musicState,
        musicEvents = musicEvents) {

        LaunchedEffect(commonState.currentTab) {
            events(CommonEvent.LoadNews)
        }

            val pagedNews = commonState.news.collectAsLazyPagingItems()
            Column (
                          modifier = Modifier
                              .fillMaxWidth()
                    ){
                LazyColumn{
                    itemsCustomized(pagedNews) { news, idx ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colors.onSurface.copy(alpha = 0.3f))
                                    .padding(15.dp)
                                    .clickable {
                                        events(CommonEvent.ChangeWebViewUrl(news!!.url))
                                        events(CommonEvent.NavigateToWebView)
                                    },
                                elevation = 10.dp
                            ) {
                                Box {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopStart)
                                    ){
                                        Image(
                                            painter = rememberAsyncImagePainter(
                                                ImageRequest.Builder(LocalContext.current)
                                                    .data(data = news!!.mainImage)
                                                    .apply(block = fun ImageRequest.Builder.() {
                                                        crossfade(true)
                                                        fallback(R.drawable.image_placeholder)
                                                        placeholder(R.drawable.image_placeholder)
                                                    }).build()
                                            ),
                                            contentDescription = "",
                                            modifier = Modifier.size(200.dp),
                                            contentScale = ContentScale.Crop,

                                            )
                                        Row(
                                            modifier = Modifier
                                                .align(Alignment.BottomCenter)
                                        ) {
                                            news!!.images.forEach { image ->
                                                Image(
                                                    painter = rememberAsyncImagePainter(
                                                        ImageRequest.Builder(LocalContext.current)
                                                            .data(data = image)
                                                            .apply(block = fun ImageRequest.Builder.() {
                                                                crossfade(true)
                                                                fallback(R.drawable.image_placeholder)
                                                                placeholder(R.drawable.image_placeholder)
                                                            }).build()
                                                    ),
                                                    contentDescription = "",
                                                    modifier = Modifier.size(50.dp),
                                                    contentScale = ContentScale.Crop,

                                                    )
                                            }
                                        }
                                    }
                                    Column(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            //.background(MaterialTheme.colors.onSurface.copy(alpha = 0.4f))
                                            .padding(15.dp)
                                    ) {
                                        Text(
                                            news!!.title,
                                            textAlign = TextAlign.Start,
                                            style = MaterialTheme.typography.h5.copy(
                                                MaterialTheme.colors.onSurface,
                                                fontWeight = FontWeight.Bold
                                            ),
                                        )
                                        Text(
                                            news!!.summary,
                                            textAlign = TextAlign.Start,
                                            style = MaterialTheme.typography.body1.copy(
                                                MaterialTheme.colors.onSurface,
                                            ),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }


    }

}