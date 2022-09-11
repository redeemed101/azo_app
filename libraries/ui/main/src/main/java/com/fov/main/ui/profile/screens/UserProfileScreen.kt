package com.fov.main.ui.profile.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fov.authentication.events.UsersEvent
import com.fov.authentication.states.UsersState
import com.fov.authentication.viewModels.UsersViewModel
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.ui.composers.general.LoadingBox
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.main.ui.home.HomeGeneralScreen
import com.fov.navigation.BackPageData
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.states.MusicState
import com.fov.sermons.states.StoredMusicState
import com.fov.sermons.viewModels.SermonViewModel
import com.fov.sermons.viewModels.StoredSermonViewModel
import kotlinx.coroutines.launch
import com.fov.common_ui.R
import com.fov.common_ui.theme.buttonHeight
import com.fov.common_ui.ui.composers.sections.UserProfileCircle
import com.fov.main.ui.profile.sections.profileTabs
import com.fov.navigation.Screen
import com.fov.payment.events.PayEvent
import com.fov.payment.states.PayState
import com.fov.payment.viewModels.PaymentViewModel
import com.fov.sermons.events.StoredMusicEvent

@Composable
fun UserProfileScreen(
    usersViewModel: UsersViewModel,
    commonViewModel: CommonViewModel,
    musicViewModel: SermonViewModel,
    storedMusicViewModel: StoredSermonViewModel,
    paymentViewModel: PaymentViewModel
){
    val userState  by usersViewModel.uiState.collectAsState()
    val commonState by commonViewModel.uiState.collectAsState()
    val storedMusicState by storedMusicViewModel.uiState.collectAsState()
    val musicState by musicViewModel.uiState.collectAsState()
    val paymentState by paymentViewModel.uiState.collectAsState()

    ProfileScreen(
        userState,
        usersViewModel::handleUsersEvent,
        commonState,
        commonViewModel::handleCommonEvent,
        storedMusicState = storedMusicState,
        storedMusicEvents = storedMusicViewModel::handleMusicEvent,
        musicState = musicState,
        musicViewModel::handleMusicEvent,
        paymentState,
        paymentViewModel::handlePaymentEvent
    )

}

@Composable
private fun ProfileScreen(
    userState: UsersState,
    userEvents: (UsersEvent) -> Unit,
    commonState: CommonState,
    commonEvents: (CommonEvent) -> Unit,
    storedMusicState: StoredMusicState,
    storedMusicEvents : (StoredMusicEvent) -> Unit,
    musicState: MusicState,
    musicEvents: (MusicEvent) -> Unit,
    payState: PayState,
    payEvents : (PayEvent) -> Unit
)
{

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    HomeGeneralScreen(
        commonState = commonState,
        events = commonEvents,
        swipeToRefreshAction = {

            userEvents(UsersEvent.UserSelected(userState.userProfileId))
        },
        backHandler = {
            commonEvents(CommonEvent.ChangeHasDeepScreen(false,""))
            commonEvents(CommonEvent.NavigateUp)
        }
    ) {
        LaunchedEffect(commonState.hasDeepScreen) {

            commonEvents(CommonEvent.ChangeBackPageData(BackPageData()))
            if( userState.userModel != null) {
                //commonEvents(CommonEvent.ChangeHasDeepScreen(true, userState.userModel!!.name))
            }
            userEvents(UsersEvent.UserSelected(userState.userProfileId))
            storedMusicEvents(StoredMusicEvent.LoadDownloadedSongs)
            storedMusicEvents(StoredMusicEvent.LoadDownloadedAlbums)

        }
        val scrollState = rememberScrollState(0)
        if(userState.isLoading){
            LoadingBox()
        }
        if( userState.userModel != null) {
            val user = userState.currentUserModel!!
            BoxWithConstraints {
                val screenWidth = maxWidth
                Column(
                    modifier = Modifier

                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(commonPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            userState.userModel!!.name,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.h5.copy(
                                MaterialTheme.colors.onSurface,
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                        Text(
                            userState.userModel!!.email,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.caption.copy(
                                MaterialTheme.colors.onSurface,
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                        Spacer(modifier = Modifier.height(commonPadding))
                        if(!userState.userModel!!.isSubscribed)
                        Button(
                            shape = MaterialTheme.shapes.medium,
                            enabled = !payState.isPaymentValid,
                            modifier = Modifier
                                .width(screenWidth * 0.6f)
                                .height(buttonHeight),

                            onClick = {
                               commonEvents(CommonEvent.ChangeBackPageData(BackPageData(true,userState.userModel!!.name)))
                               commonEvents(CommonEvent.ChangeHasDeepScreen(true, "Payment Options"))
                               payEvents(PayEvent.GoToOptions)

                            }) {

                            Text(
                                "Subscribe",
                                color = MaterialTheme.colors.surface
                            )


                        }


                    }
                    profileTabs(
                        commonState,
                        commonEvents,
                        storedMusicState,
                        storedMusicEvents,
                        musicState,
                        musicEvents
                    )


                }
            }
        }
    }


}