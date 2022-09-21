package com.fov.main.ui.sermons.library.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common_ui.utils.helpers.ShimmerAnimation
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.extensions.itemsCustomized
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.bottomTabHeight
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.utils.helpers.Utilities
import com.fov.main.ui.sermons.audio.general.SongListItem
import com.fov.main.ui.sermons.audio.screens.SongBottomSheetHeader
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.events.StoredMusicEvent
import com.fov.sermons.models.Song
import com.fov.sermons.states.MusicState
import com.fov.sermons.states.StoredMusicState


@Composable
fun DownloadedSongsTab(
    storedMusicState: StoredMusicState,
    storedMusicEvents : (event: StoredMusicEvent) -> Unit,
    musicState: MusicState,
    musicEvents : (event: MusicEvent) -> Unit,
    commonState: CommonState,
    events : (event: CommonEvent) -> Unit
){
    val items = storedMusicState.downloadedSongs.collectAsLazyPagingItems()

    BoxWithConstraints {

        val totalWidth = maxWidth
        if(items != null){
            LazyColumn {
                itemsCustomized(items!!) { dSong,idx ->

                    val song = Song.ModelMapper.fromDownloadedSong(dSong!!)
                    SongListItem(song =song!!,
                        showArtwork = true,
                        isDownloadedSong = true,
                        onDownloadedIconClicked = {
                          com.fov.sermons.utils.helpers.Utilities.unDownloadSong(""){
                              storedMusicEvents(StoredMusicEvent.DeleteDownloadedSong(song.songId))
                          }
                    },
                    rowClick = {
                        //musicEvents(MusicEvent.SaveRecentSearch(song!!))

                        musicEvents(MusicEvent.SongSelected(song!!))
                        //musicEvents(MusicEvent.ChangeShowingSong(true))
                        }
                    ) {
                        musicEvents(MusicEvent.ChangeSongSelected(song!!))
                        musicEvents(MusicEvent.ChangeShowingSong(true))
                        events(CommonEvent.ChangeBottomSheetHeader{
                            SongBottomSheetHeader(song!!)
                        })
                         commonState.bottomSheetAction()
                    }
                }
                items.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .background(color = MaterialTheme.colors.background),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.testTag("TAG_PROGRESS"),
                                        color = MaterialTheme.colors.onBackground
                                    )
                                }
                            }
                        }
                        loadState.append is LoadState.Loading -> {
                            item { ShimmerAnimation(size = 60.dp, isCircle = true) }
                        }
                        loadState.refresh is LoadState.Error -> {
                            val e = items.loadState.refresh as LoadState.Error
                            item {

                            }
                        }
                        loadState.append is LoadState.Error -> {
                            val e = items.loadState.append as LoadState.Error
                            item {

                            }
                        }
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