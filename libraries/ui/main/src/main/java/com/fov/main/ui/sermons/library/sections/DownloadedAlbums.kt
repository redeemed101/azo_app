package com.fov.main.ui.sermons.library.sections

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.bottomTabHeight
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.theme.padding10
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.events.StoredMusicEvent
import com.fov.sermons.models.Album
import com.fov.sermons.states.MusicState
import com.fov.sermons.states.StoredMusicState
import com.fov.sermons.ui.albums.AlbumItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DownloadedAlbumsTab(
    storedMusicState: StoredMusicState,
    storedMusicEvents : (event: StoredMusicEvent) -> Unit,
    musicState: MusicState,
    musicEvents : (event: MusicEvent) -> Unit,
    commonState: CommonState,
    events : (event: CommonEvent) -> Unit
){
    val items = storedMusicState.downloadedAlbums.collectAsLazyPagingItems()
    BoxWithConstraints {
        val totalWidth = maxWidth
        if(items != null){
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(padding10),

                ) {
                items(items.itemCount) { index ->
                    val album  = Album.ModelMapper.fromDownloadedAlbum(items[index]!!)
                    AlbumItem(album = album, isDownloaded = true, onDownloadedIconClicked = {

                    }){
                        musicEvents(MusicEvent.AlbumSelected(album))
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