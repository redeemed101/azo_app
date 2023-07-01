package com.fov.sermons.ui.music

import android.net.Uri
import android.view.View
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.fov.common_ui.theme.White009
import com.fov.sermons.databinding.ExoPlayerCustomBinding
import com.fov.sermons.databinding.ExoplayerMainCustomBinding
import com.fov.sermons.utils.MediaPlayback
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.util.RepeatModeUtil
import com.google.android.exoplayer2.Player.MediaItemTransitionReason
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView


@Composable
fun MiniMusicPlayer(
    modifier: Modifier,
    exoPlayer: ExoPlayer,
    sources : List<String>,
    onMediaReady : ()  ->Unit  = {},
    onMediaError : ()  ->Unit  = {},
    onMediaBuffering : ()->Unit  =  {},
    onMediaEnded  :  ()  -> Unit =  {},
    minimized: Boolean = false,
    alreadyPlaying : Boolean = false,
    onClicked : () -> Unit = {}
) : MediaPlayback {

    if(!alreadyPlaying)
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
            exoPlayer.addListener(object: Player.Listener{

                override fun onMediaItemTransition( mediaItem: MediaItem?, @MediaItemTransitionReason reason: Int) {

                    //updateUiForPlayingMediaItem(mediaItem)
                }

                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {

                    when (playbackState){
                        Player.STATE_BUFFERING->
                            onMediaBuffering()
                        Player.STATE_READY->
                            onMediaReady()
                        Player.STATE_ENDED->
                            onMediaEnded()
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    onMediaError()
                }


            })



        }
        val color = White009.toArgb()
        if (minimized) {
            AndroidViewBinding(
                ExoPlayerCustomBinding::inflate,
                modifier = modifier) {

                player.apply {
                    player = exoPlayer
                    setOnClickListener {
                        onClicked()
                    }
                    setShutterBackgroundColor(color)
                    useArtwork = false
                    showController()
                    controllerHideOnTouch = false
                    controllerShowTimeoutMs = 0
                    setRepeatToggleModes(RepeatModeUtil.REPEAT_TOGGLE_MODE_ONE or RepeatModeUtil.REPEAT_TOGGLE_MODE_ALL)
                    setShowShuffleButton(true)

                }

            }
        }
        else{


            AndroidViewBinding(
                ExoplayerMainCustomBinding::inflate,
                modifier = modifier) {

                mainPlayer.apply {


                    player = exoPlayer
                    if( alreadyPlaying)
                      visibility = View.VISIBLE
                    //setShutterBackgroundColor(color)
                    useArtwork = false
                    showController()
                    controllerHideOnTouch = false
                    controllerShowTimeoutMs = 0
                    setRepeatToggleModes(RepeatModeUtil.REPEAT_TOGGLE_MODE_ONE or RepeatModeUtil.REPEAT_TOGGLE_MODE_ALL)
                    setShowShuffleButton(true)

                }

            }


        }
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