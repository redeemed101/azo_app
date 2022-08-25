package com.fov.main.ui.sermons.audio.general

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.fov.common_ui.R
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.DarkThemeBarsBlack
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.theme.padding10
import com.fov.common_ui.ui.composers.general.LoadingBox
import com.fov.common_ui.ui.composers.sections.SearchBar
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.states.MusicState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@ExperimentalAnimationApi
@Composable
fun MusicGeneralScreen(
    commonState : CommonState,
    events: (event: CommonEvent) -> Unit,
    musicState: MusicState,
    musicEvents: (event: MusicEvent) -> Unit,
    swipeToRefreshAction : () -> Unit = {},
    backHandler : () -> Unit = {},
    onSearch : (String) -> Unit = {},
    onSearchClose : () -> Unit = {},
    onCloseSearch : () -> Unit = {},
    pageContent : @Composable BoxScope.() -> Unit,
){

        BackHandler(onBack = {
            backHandler()
        })
        Surface(
            color = MaterialTheme.colors.surface,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

        ) {


            Box {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(commonState.isRefreshing),
                    onRefresh = {
                        swipeToRefreshAction()
                    },
                ) {
                    if (musicState.isLoading) {
                        LoadingBox()
                    } else {
                        BoxWithConstraints {
                            val totalWidth = maxWidth
                            Column {
                                if (commonState.showSearchBar) {

                                    var searchField by remember {
                                        mutableStateOf(
                                            TextFieldValue(
                                                commonState.search
                                            )
                                        )
                                    }
                                    SearchBar(
                                        value = searchField,
                                        onChange = {
                                            events(CommonEvent.SearchTextChanged(it.text))
                                            searchField = it
                                        },
                                        onSearch = {

                                            if (searchField.text.isNotEmpty()) {
                                                onSearch(searchField.text)
                                            }
                                        },
                                        onClose = {
                                            events(CommonEvent.SearchTextChanged(""))
                                            searchField = TextFieldValue()
                                            onSearchClose()
                                        },
                                        onCloseSearch = {
                                            events(CommonEvent.ChangeShowSearchBar(false))
                                            events(CommonEvent.ChangeHasTopBar(true))
                                            onCloseSearch()
                                        },
                                        width = totalWidth

                                    )
                                }
                                Box {
                                    pageContent()
                                }
                                Spacer(modifier = Modifier.height(100.dp))
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .systemBarsPadding()
                ) {

                    if (musicState.nowPlaying.isNotEmpty() && commonState.hasBottomBar) {

                        BottomMusicPlayer(
                            musicState = musicState,
                            commonState = commonState,
                            commonEvents = events,
                            musicEvents = musicEvents
                        )
                    }


                }
            }


        }

}
