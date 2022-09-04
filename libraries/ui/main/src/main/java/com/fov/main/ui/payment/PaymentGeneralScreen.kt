package com.fov.main.ui.payment

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.ui.composers.general.LoadingBox
import com.fov.common_ui.ui.composers.sections.SearchBar
import com.fov.main.ui.sermons.audio.general.BottomMusicPlayer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun PaymentGeneralScreen(
    commonState : CommonState,
    events: (event: CommonEvent) -> Unit,
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
                if (commonState.loading) {
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

               //general thing


            }
        }
    }

}