package com.fov.sermons.ui.music

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.fov.sermons.utils.MediaPlayback
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.util.RepeatModeUtil.REPEAT_TOGGLE_MODE_ALL
import com.google.android.exoplayer2.util.RepeatModeUtil.REPEAT_TOGGLE_MODE_ONE


@Composable
fun MusicPlayer (
                 modifier: Modifier,
                 sources : List<String> ,
                 onMediaReady : ()  ->Unit  = {},
                 onMediaError : ()  ->Unit  = {},
                 onMediaBuffering : ()->Unit  =  {},
                 onMediaEnded  :  ()  -> Unit =  {}
                )
        : MediaPlayback {

    val context = LocalContext.current

    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    LaunchedEffect(sources) {

        val mediaItems: MutableList<MediaItem> = ArrayList()
        sources.forEach { source ->
            mediaItems.add(MediaItem.fromUri( Uri.parse(
                source
            )))
        }

        exoPlayer.setMediaItems(mediaItems)
        exoPlayer.playWhenReady = true
        exoPlayer.prepare()
        exoPlayer.addListener(object:Player.Listener{


            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {

                when (playbackState){
                    Player.STATE_BUFFERING->
                        onMediaBuffering()
                    Player.STATE_READY-> {
                        onMediaReady()

                    }
                    Player.STATE_ENDED->
                        onMediaEnded()
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                 onMediaError()
            }


        })



    }



    AndroidView(
        factory = { context ->
            StyledPlayerView(context).apply {
                
                player = exoPlayer
                useArtwork = false
                controllerHideOnTouch = false
                controllerShowTimeoutMs = 0
                setRepeatToggleModes(REPEAT_TOGGLE_MODE_ONE or REPEAT_TOGGLE_MODE_ALL)
                setShowShuffleButton(true)


            }
        },
        modifier = modifier
    )
    return remember(exoPlayer) {
        object: MediaPlayback {
            override fun playPause() {
                exoPlayer.playWhenReady = !exoPlayer.playWhenReady
            }
            override fun toggleRepeatMode(repeatMode : Int){
                exoPlayer.repeatMode = repeatMode
            }

            override fun addMediaToPlaylist(media: MediaItem) {
                exoPlayer.addMediaItem(media)
            }

            override fun addMediaToPlaylist(media: List<MediaItem>) {
                media.forEach { m ->
                    exoPlayer.addMediaItem(m)
                }
            }

            override fun songReady() {
                TODO("Not yet implemented")
            }

            override fun forward(durationInMillis: Long) {
                exoPlayer.seekTo(exoPlayer.currentPosition + durationInMillis)
                exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
            }

            override fun rewind(durationInMillis: Long) {
                exoPlayer.seekTo(exoPlayer.currentPosition - durationInMillis)
            }
        }
    }
}