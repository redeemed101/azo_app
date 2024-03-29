package com.fov.sermons.ui.albums

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common_ui.utils.helpers.ShimmerAnimation
import com.fov.common_ui.extensions.itemsCustomized
import com.fov.common_ui.theme.AzoTheme
import com.fov.common_ui.ui.composers.sections.Section
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.mock.data.albums.ALBUMS
import com.fov.sermons.models.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Preview(showBackground = true)
@Composable
fun prevTopAlbums(){
    AzoTheme {
        Surface(color = MaterialTheme.colors.surface, modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()

        ) {
            Albums("Top Albums", flowOf(PagingData.from(
                ALBUMS
            )), {

            }
            ) { }
        }
    }
}
@Composable
fun Albums(
    title: String,
    albums: Flow<PagingData<Album>>,
    musicEvents: (event: MusicEvent) -> Unit,
    navigateTabAction: () -> Unit
){
    val lazyAlbumItems = albums.collectAsLazyPagingItems()
    when(lazyAlbumItems.loadState.refresh){
        is LoadState.Loading -> {
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ShimmerAnimation(size = 100.dp, isCircle = false)
                ShimmerAnimation(size = 100.dp, isCircle = false)
            }
        }
        is LoadState.Error -> {

        }
        else -> {
            if(lazyAlbumItems.itemCount > 0)
                Section(title,{}){

                    LazyRow(modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        itemsCustomized(lazyAlbumItems){ album,_ ->
                            AlbumItem(album = album!!, onDownloadedIconClicked = {}){

                                musicEvents(MusicEvent.AlbumSelected(album!!))
                                musicEvents(MusicEvent.ChangeShowingAlbum(true))
                                navigateTabAction()
                            }
                        }
                    }

                }
        }
    }

}