package com.fov.sermons.ui.video
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YouTubeVideoPlayer(
    videoId : String = "S0Q4gqBUs7c",
    modifier: Modifier,
    onReady: () -> Unit = {}
){
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = { context ->
           YouTubePlayerView(context).apply {

               lifecycleOwner.lifecycle.addObserver(this)
               addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                   override fun onReady(youTubePlayer: YouTubePlayer) {
                       youTubePlayer.loadVideo(videoId, 0F);
                       onReady()
                   }


                   override fun onStateChange(
                       youTubePlayer: YouTubePlayer,
                       state: PlayerConstants.PlayerState
                   ) {
                       onReady()
                   }

                   override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                       onReady()
                   }

               })

            }
        },
        modifier = modifier
    )
}
