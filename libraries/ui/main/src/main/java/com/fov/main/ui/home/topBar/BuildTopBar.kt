package com.fov.main.ui.home.topBar

import android.content.Context
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LifecycleOwner
import com.fov.common_ui.ui.composers.headers.mainTopBar
import com.fov.sermons.ui.general.headers.mainMusicTab
import com.fov.authentication.events.UsersEvent
import com.fov.authentication.states.UsersState
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.White009
import com.fov.common_ui.ui.composers.general.searchHeaderIcon
import com.fov.common_ui.ui.composers.headers.backTopBar
import com.fov.main.ui.general.moreHeaderIcon
import com.fov.main.ui.home.sections.albumBottomSheet
import com.fov.main.ui.home.sections.ownerUserBottomSheet
import com.fov.main.ui.home.sections.songBottomSheet
import com.fov.main.ui.home.sections.userBottomSheet
import com.fov.navigation.BackPageData
import com.fov.navigation.Screen
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.events.StoredMusicEvent
import com.fov.sermons.states.MusicState
import com.fov.sermons.ui.general.headers.mainVideoTab
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BuildTopBar(
    backgroundColor: Color = MaterialTheme.colors.onSurface,
    tintColor : Color =   MaterialTheme.colors.surface,
    scope : CoroutineScope,
    context : Context,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    lifecycleOwner: LifecycleOwner,
    commonState: CommonState,
    events : (CommonEvent) -> Unit,
    userState: UsersState,
    userEvents : (UsersEvent) -> Unit,
    musicState: MusicState,
    musicEvents :  (MusicEvent) -> Unit,
    storedMusicEvents : (StoredMusicEvent) -> Unit,
    isSongDownloaded:Boolean,

    ) : @Composable () -> Unit {



    return {
        if (commonState.hasTopBar) {
            if (commonState.hasDeepScreen) {



                backTopBar(
                    title = commonState.screenTitle,
                    backgroundColor = backgroundColor,
                    tintColor = tintColor,
                    backAction = { isDeep, title ->
                        events(CommonEvent.ChangeHasDeepScreen(isDeep, title))
                        events(CommonEvent.ChangeShowMoreOptions(false))
                        events(CommonEvent.ChangeTopBarColor(White009))
                        events(CommonEvent.ChangeShowSearchOption(false))
                        musicEvents(MusicEvent.ChangeShowingSong(false))
                        events(CommonEvent.NavigateUp)
                    }, backPageData = commonState.backPageData
                ) {
                    if (commonState.showMoreOptions) {
                        var bottomSheetShowMoreContent: () -> Unit = {}
                        if (commonState.currentTab == Screen.Home) {
                            if (commonState.user?.id == userState.userModel?.id) {
                                bottomSheetShowMoreContent = {
                                    ownerUserBottomSheet(
                                        events = events,
                                        userEvents = userEvents,
                                        scope = scope,
                                        bottomSheetScaffoldState = bottomSheetScaffoldState,
                                        context = context,
                                        lifecycleOwner = lifecycleOwner,
                                        "@${commonState.user?.name}"

                                    )
                                }
                            } else {
                                bottomSheetShowMoreContent = {
                                    userBottomSheet(
                                        events = events,
                                        userEvents = userEvents,
                                        scope = scope,
                                        bottomSheetScaffoldState = bottomSheetScaffoldState,
                                        context = context,
                                        lifecycleOwner = lifecycleOwner,
                                        userState.currentUserModel!!.name
                                    )
                                }

                            }
                        }
                        if (musicState.showingAlbum) {
                            val album = musicState.selectedAlbum!!
                            val isLiked = album.userLikes.contains(commonState.user!!.id)
                            bottomSheetShowMoreContent = {
                                albumBottomSheet(
                                    album = album,
                                    events = events,
                                    storedMusicEvents = storedMusicEvents,
                                    scope = scope,
                                    bottomSheetScaffoldState = bottomSheetScaffoldState,
                                    context = context,
                                    lifecycleOwner = lifecycleOwner,
                                    isDownloaded = isSongDownloaded,
                                    isLiked = isLiked,
                                    musicEvents = musicEvents
                                )
                            }
                        }
                        if (musicState.showingSong) {
                            val song = musicState.selectedSong!!
                            val isLiked = false//song.userLikes.contains(commonState.user!!.id)
                            bottomSheetShowMoreContent = {
                                songBottomSheet(
                                    song = song,
                                    events = events,
                                    storedMusicEvents = storedMusicEvents,
                                    scope = scope,
                                    bottomSheetScaffoldState = bottomSheetScaffoldState,
                                    context = context,
                                    lifecycleOwner = lifecycleOwner,
                                    isDownloaded = isSongDownloaded,
                                    isLiked = isLiked,
                                    musicEvents = musicEvents
                                )
                            }
                        }
                        events(
                            CommonEvent.ChangeBottomSheetAction(
                                bottomSheetShowMoreContent
                            )
                        )
                        moreHeaderIcon(
                            tintColor = tintColor,
                            onAction = bottomSheetShowMoreContent
                        )


                    } else if (commonState.showSearchOption) {
                        searchHeaderIcon(
                            tintColor = tintColor
                        ) {
                            events(CommonEvent.ChangeShowSearchBar(true))
                            events(CommonEvent.ChangeHasTopBar(false))
                        }
                    }
                }
            } else {
                when (commonState.currentTab) {
                    Screen.Home -> {
                        mainTopBar(
                            backgroundColor = backgroundColor,
                            tintColor = tintColor,
                            numNotifications = userState.numNotifications,
                            notificationClicked = {
                                events(CommonEvent.ChangeHasDeepScreen(true, "Notifications"))
                                events(CommonEvent.ChangeBackPageData(BackPageData()))
                                events(CommonEvent.NotificationsViewed)
                                userEvents(UsersEvent.LoadNotifications)
                                events(CommonEvent.NavigateToNotifications)
                                //navController.navigate(Screen.Notifications.route.destination)
                            },
                            profileClicked = {

                            },
                            searchClicked = {

                                events(CommonEvent.ChangeHasDeepScreen(true, "Search"))

                                events(CommonEvent.HasSearched(false))
                                events(CommonEvent.NavigateToSearch)

                            }
                        )
                    }
                    Screen.Music -> {
                        mainMusicTab(
                            backgroundColor = backgroundColor,
                            tintColor = tintColor,
                        ) {
                            events(CommonEvent.ChangeHasDeepScreen(true, "Search"))
                            events(CommonEvent.NavigateToSearch)
                        }
                    }
                    Screen.Video -> {
                        mainVideoTab(
                            backgroundColor = backgroundColor,
                            tintColor = tintColor,
                        ) {
                            events(CommonEvent.ChangeHasDeepScreen(true, "Search"))
                            events(CommonEvent.NavigateToSearch)
                        }
                    }

                    else -> {

                    }
                }
            }
        }
    }

}



