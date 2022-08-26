package com.fov.main.ui.home.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common_ui.utils.helpers.ShimmerAnimation
import com.fov.authentication.events.UsersEvent
import com.fov.authentication.states.UsersState
import com.fov.authentication.viewModels.UsersViewModel
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.bottomTabHeight
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.ui.composers.sections.SearchField
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.main.events.MainEvent
import com.fov.main.states.MainState
import com.fov.main.ui.home.HomeGeneralScreen
import com.fov.main.viewModels.MainViewModel
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.states.MusicState
import com.fov.sermons.ui.music.MusicSection
import com.fov.sermons.viewModels.SermonViewModel


@ExperimentalComposeUiApi
@Composable
fun SearchScreen(
    commonViewModel: CommonViewModel,
    mainViewModel: MainViewModel,
    usersViewModel: UsersViewModel,
    musicViewModel: SermonViewModel


) {
    val state by mainViewModel.uiState.collectAsState()
    val commonState by commonViewModel.uiState.collectAsState()
    val usersState by usersViewModel.uiState.collectAsState()
    val musicState by musicViewModel.uiState.collectAsState()
   Search(
       commonState = commonState,
       mainState = state,
       events = commonViewModel::handleCommonEvent,
       mainEvents = mainViewModel::handleMainEvent,
       usersState = usersState,
       usersEvents = usersViewModel::handleUsersEvent,
       musicState = musicState,
       musicEvents = musicViewModel::handleMusicEvent

   )
}

@ExperimentalComposeUiApi
@Composable
private fun Search(
    commonState: CommonState,
    mainState : MainState,
    events: (event: CommonEvent) -> Unit,
    mainEvents: (event: MainEvent) -> Unit,
    usersState: UsersState,
    usersEvents : (event : UsersEvent) -> Unit,
    musicState : MusicState,
    musicEvents : (event : MusicEvent) -> Unit
) {
    HomeGeneralScreen(
        commonState = commonState,
        events = events,
        swipeToRefreshAction = {

        },
        backHandler = {
            events(CommonEvent.ChangeHasDeepScreen(false,""))
            events(CommonEvent.HasSearched(false))
            events(CommonEvent.NavigateUp)
        }
    ) {
        val scrollState = rememberScrollState(0)
        val keyboardController = LocalSoftwareKeyboardController.current
        BoxWithConstraints {
            val totalWidth = maxWidth
            Column(
                modifier = Modifier
                    .verticalScroll(
                        scrollState
                    )
            ) {

                Spacer(modifier = Modifier.height(10.dp))
                var searchText by remember { mutableStateOf(TextFieldValue(usersState.searchUserText)) }
                SearchField(
                    value = searchText,
                    placeholder = "sermons",
                    onChange = {
                        usersEvents(UsersEvent.SearchUserTextChanged(it.text))
                        searchText = it
                    },
                    width = totalWidth,
                    height = 50.dp,
                    padding = totalWidth * 0.1f,
                    keyboardOptions = KeyboardOptions(

                        imeAction = ImeAction.Search,
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            keyboardController?.hide()
                            //do search
                            if (searchText.text.isNotEmpty()) {
                                musicEvents(MusicEvent.SearchSong(searchText.text))
                                usersEvents(UsersEvent.SearchUser(searchText.text))
                                events(CommonEvent.HasSearched(true))
                            }
                        }
                    ),
                    onClose = {

                        mainEvents(MainEvent.SearchTextChanged(""))
                        searchText = TextFieldValue()
                        events(CommonEvent.HasSearched(false))
                        musicEvents(MusicEvent.LoadRecentSearch)

                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                if(commonState.hasSearchResult){
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(horizontal = commonPadding)
                            .fillMaxWidth()
                    ) {
                        Text(
                            "Search Results for '${usersState.searchUserText}'",
                            modifier = Modifier.padding(top = 4.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h5.copy(
                                MaterialTheme.colors.onSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp

                            ),
                        )
                    }
                }
                else{
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(horizontal = commonPadding)
                            .fillMaxWidth()
                    ) {
                        Text(
                            "Recent Searches",
                            modifier = Modifier.padding(top = 4.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h5.copy(
                                MaterialTheme.colors.onSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp

                            ),
                        )
                        Text(
                            "Clear",
                            modifier = Modifier.clickable {
                                usersEvents(UsersEvent.ClearRecentUserSearch)
                                musicEvents(MusicEvent.ClearRecentSongSearch)
                                musicEvents(MusicEvent.LoadRecentSearch)
                            },
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6.copy(
                                Color.DarkGray,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp

                            ),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                if(usersState.isLoading || musicState.isLoading){
                    ShimmerAnimation(size = 100.dp, isCircle =false )
                }
                Column {

                    Spacer(modifier = Modifier.height(10.dp))
                    MusicSection(
                        title = "Music",
                        hasSearchResult = commonState.hasSearchResult,
                        musicState.recentSongSearch, musicEvents
                    )
                }

                Spacer(modifier = Modifier.height(commonPadding))
                Spacer(modifier = Modifier.height(bottomTabHeight))

            }

        }
    }


}







