package com.fov.sermons.ui.music

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.fov.common_ui.ui.composers.sections.Section
import com.fov.common_ui.theme.AzoTheme
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.mock.data.songs.SONGS
import com.fov.sermons.models.Song
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Preview(showBackground = true)
@Composable
fun prevForYou(){
    AzoTheme {
        Surface(color = MaterialTheme.colors.surface, modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()

        ) {
            MusicSection("For You", false,flowOf(PagingData.from(
               SONGS
            )),{}){

            }
        }
    }
}
@Composable
fun MusicSection(title : String,
                 hasSearchResult: Boolean,
                 songs: Flow<PagingData<Song>>,
                 musicEvents : (event : MusicEvent) -> Unit,
                 navigateTabAction : ()-> Unit = {}
){
    val lazySongItems = songs.collectAsLazyPagingItems()
    if(title != "For You") {
        LaunchedEffect(title) {
            if (!hasSearchResult)
                musicEvents(MusicEvent.LoadRecentSearch)
        }
    }
    if(lazySongItems.itemCount > 0)
    Section(title,{}){


        LazyRow(modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(lazySongItems){ song ->
                MusicItem(song = song!!){
                    navigateTabAction()
                   musicEvents(MusicEvent.SaveRecentSearch(song!!))
                   musicEvents(MusicEvent.SongSelected(song!!))
                   musicEvents(MusicEvent.ChangeShowingSong(true))

                }
            }
        }

    }
    /*else
        ShimmerAnimation(size = 100.dp, isCircle = false)*/
}