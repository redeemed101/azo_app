package com.fov.sermons.ui.video.youtube

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.fov.domain.BuildConfig
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView


@Composable
fun TubeVideoPlayer(
    apiKey: String = BuildConfig.YOUTUBE_KEY,
    videoId: String = "HzeK7g8cD0Y",
    modifier: Modifier,
    onReady: () -> Unit = {}
){
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = { context ->
            YouTubePlayerView(context).apply {
                initialize(apiKey,
                    object: YouTubePlayer.OnInitializedListener{
                        override fun onInitializationSuccess(
                            p0: YouTubePlayer.Provider?,
                            youTubePlayer: YouTubePlayer?,
                            p2: Boolean
                        ) {
                            youTubePlayer?.loadVideo(videoId);
                            youTubePlayer?.play();
                        }

                        override fun onInitializationFailure(
                            p0: YouTubePlayer.Provider?,
                            p1: YouTubeInitializationResult?
                        ) {
                            Toast.makeText(context, "Video player Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                )
            }
        },
        modifier = modifier
    )
}