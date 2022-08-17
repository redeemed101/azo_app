package com.fov.main.ui.sermons.audio.general

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.padding10
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.states.MusicState
import com.fov.sermons.ui.MusicPlayerSheet
import com.fov.sermons.ui.music.MiniMusicPlayer
import com.fov.common_ui.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomMusicPlayer(
    commonState: CommonState,
    commonEvents : (event: CommonEvent) -> Unit,
    musicState : MusicState,
    musicEvents : (event: MusicEvent) -> Unit,
){

    BoxWithConstraints{
        val maxHeight = maxHeight
        val maxWidth = maxWidth
        val  context  = LocalContext.current
        var isSongLoaded by
        remember { mutableStateOf(true) }




        MusicPlayerContainer(
            commonState,
            commonEvents,
            musicState,
            musicEvents,

            ) {
            if( musicState.player != null) {
                val playerBox = Column {
                    val player = MiniMusicPlayer(
                        modifier = Modifier
                            .background(MaterialTheme.colors.onSurface.copy(alpha = 1f))
                            .fillMaxWidth()
                            .padding(bottom = if (musicState.isPlayerMinimized) 50.dp else 0.dp)
                            .height(if (musicState.isPlayerMinimized) 80.dp else maxHeight / 5f),
                        exoPlayer = musicState.player!!,
                        onClicked = {
                            if (musicState.isPlayerMinimized) {
                                musicEvents(MusicEvent.MinimizeMusicPlayer(false))
                                commonEvents(CommonEvent.ChangeHasTopBar(false))
                                //commonEvents(CommonEvent.ChangeHasBottomBar(false))
                            }
                        },
                        sources = musicState.nowPlaying.map { song ->
                            song.path
                        },
                        alreadyPlaying = musicState.isSongStarted,
                        onMediaReady = {
                            isSongLoaded = true
                            musicEvents(MusicEvent.SongStarted(true))
                        },
                        onMediaBuffering = {
                            isSongLoaded = false
                        },
                        onMediaError = {
                            isSongLoaded = false
                            Toast.makeText(context, "Loading song failed", Toast.LENGTH_LONG)
                        },
                        minimized = musicState.isPlayerMinimized
                    )
                    musicEvents(MusicEvent.SetMediaPlayback(player))
                }
            }
        }


    }
}
@ExperimentalAnimationApi
@Composable
fun MusicPlayerContainer(
    commonState: CommonState,
    commonEvents : (event: CommonEvent) -> Unit,
    musicState : MusicState,
    musicEvents : (event: MusicEvent) -> Unit,
    musicPlayer :  @Composable() () -> Unit = {}
){

    AnimatedVisibility(
        visible = musicState.isPlayerMinimized,
    ) {
        Column {

            Box {


                musicPlayer()


                Row(
                    modifier = Modifier
                        .padding(vertical = 30.dp)
                        .align(Alignment.CenterStart)
                        .fillMaxWidth(fraction = 0.7f)
                        .clickable {

                        }
                        .padding(horizontal = padding10)
                        .offset(y = (-30).dp)
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = musicState.currentSong!!.artwork,
                            builder = {
                                crossfade(true)
                                fallback(R.drawable.image_placeholder)
                                placeholder(R.drawable.image_placeholder)
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            //.align(Alignment.CenterStart)
                            .size(50.dp)
                    )
                    Column(
                        modifier = Modifier.padding(horizontal = padding10)
                    ) {
                        Text(

                            musicState.currentSong!!.songName,
                            // modifier = Modifier.padding(top = 10.dp),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.h5.copy(
                                MaterialTheme.colors.onSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp

                            ),
                        )
                        Text(
                            "Apostle Ziba",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.caption.copy(
                                MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                                fontSize = 12.sp

                            ),
                        )
                    }
                }
            }
        }
    }

    AnimatedVisibility(
        visible = !musicState.isPlayerMinimized,
        enter = slideInVertically(
            // Slide in from 50 dp from the top.
            initialOffsetY = { 50 }
        ) + expandVertically(
            // Expand from the top.
            expandFrom = Alignment.Top
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        MusicPlayerSheet(
            commonState = commonState,
            commonEvents = commonEvents,
            musicState = musicState ,
            musicEvents =musicEvents,
            musicPlayer =  musicPlayer
        )
    }

}