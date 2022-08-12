package com.fov.sermons.ui.video

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.fov.sermons.utils.MediaPlayback
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

@Composable
fun VideoPlayer (source : String = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
: MediaPlayback{

    val context = LocalContext.current

    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    LaunchedEffect(source) {

        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(context,
            Util.getUserAgent(context, context.packageName))

        val source = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri((
                source
            )))

        exoPlayer.setMediaSource(source)
        exoPlayer.prepare()
    }

    exoPlayer.playWhenReady = true

    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer

            }
        }
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
                TODO("Not yet implemented")
            }

            override fun addMediaToPlaylist(media: List<MediaItem>) {
                TODO("Not yet implemented")
            }

            override fun songReady() {
                TODO("Not yet implemented")
            }

            override fun forward(durationInMillis: Long) {
                exoPlayer.seekTo(exoPlayer.currentPosition + durationInMillis)
            }

            override fun rewind(durationInMillis: Long) {
                exoPlayer.seekTo(exoPlayer.currentPosition - durationInMillis)
            }
        }
    }
}