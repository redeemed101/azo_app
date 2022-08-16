package com.fov.sermons.ui.music


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.fov.common_ui.extensions.itemsCustomized
import com.fov.domain.database.models.DownloadedSong
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.events.StoredMusicEvent
import com.fov.sermons.models.Song
import com.fov.sermons.states.MusicState
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.coroutines.flow.Flow



@Composable
fun songColumn(
    title : String,
    songs : Flow<PagingData<DownloadedSong>>,
    musicState: MusicState,
    musicEvents: (MusicEvent) -> Unit
){

    val  context  = LocalContext.current
    var exoPlayer = musicState.player
    if(musicState.player == null) {
        exoPlayer = remember {
            ExoPlayer.Builder(context).build()
        }
    }

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
                    {
                        if (musicState.player == null){
                            musicEvents(MusicEvent.LoadPlayer(exoPlayer!!))
                        }
                        musicEvents(MusicEvent.PlaySong(Song.ModelMapper.fromDownloadedSong(song!!)))
                    }


                )
            }
        }


    }
}