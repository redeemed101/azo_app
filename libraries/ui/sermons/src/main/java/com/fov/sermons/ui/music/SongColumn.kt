package com.fov.sermons.ui.music


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.fov.common_ui.extensions.itemsCustomized
import com.fov.domain.database.models.DownloadedSong
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.events.StoredMusicEvent
import com.fov.sermons.models.Song
import kotlinx.coroutines.flow.Flow



@Composable
fun songColumn(
    title : String,
    songs : Flow<PagingData<DownloadedSong>>,
    musicEvents: (MusicEvent) -> Unit
){

    val lazySongItems = songs.collectAsLazyPagingItems()
    if(lazySongItems.itemCount > 0)
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            title,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h5.copy(
                MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold,
            ),
        )
        LazyRow{
            itemsCustomized(lazySongItems){ song,index ->
                songRow(
                    "${index + 1}",
                    song!!.songName,
                    song!!.imagePath,
                    {musicEvents(MusicEvent.PlaySong(Song.ModelMapper.fromDownloadedSong(song!!)))}


                )
            }
        }


    }
}