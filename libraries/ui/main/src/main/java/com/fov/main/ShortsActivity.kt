package com.fov.main

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.VideoView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.constraintlayout.widget.ConstraintLayout
import com.fov.common_ui.utils.helpers.NotificationUtils
import com.fov.core.utils.NetworkWatcher
import com.fov.shorts.models.ShortsView
import com.fov.shorts.utils.ShortsCallback
import com.fov.shorts.viewModels.ShortViewModel
import com.fov.shorts.views.Shorts
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ShortsActivity : AppCompatActivity(), ShortsCallback {
    private val shortViewModel : ShortViewModel by viewModels()
    @Inject
    lateinit var networkWatcher: NetworkWatcher

    var shortId  = ""
    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shorts)

        //notification channel
        NotificationUtils.createNotificationChannel(this)

        val layout = findViewById<ConstraintLayout>(R.id.container)
        val backgroundColor = Color.Black.toArgb()
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = backgroundColor
        }
        supportActionBar?.hide()

        layout.setBackgroundColor(backgroundColor)
        val container = findViewById<ConstraintLayout>(R.id.container)
        //Load real content of story
        val listOfViews = mutableListOf<ShortsView>()

        GlobalScope.launch {
            networkWatcher.watchNetwork().collectLatest { connected ->
                Log.d("NETWORK", "Network In App: $connected")
            }
        }

        val short = shortViewModel.uiState.value.short
        if(short != null){
            var videoView = VideoView(this)
            val uri = Uri.parse(short.path)
            videoView.setVideoURI(uri)
            val duration = videoView.duration
            listOfViews.add(ShortsView(videoView, 5))

            if (listOfViews.isNotEmpty()) {

                Shorts(this, listOfViews, container, this).start()
            }
        }
        else{
            finish()
        }


    }
    override fun done() {
        finish()
    }

    override fun onNextCalled(view: View, shorts: Shorts, index: Int) {
        if (view is VideoView) {
            shorts.pause(true)
            playVideo(view, index, shorts)
        }
    }

    private fun playVideo(videoView: VideoView, index: Int, shorts: Shorts) {

        videoView.requestFocus()
        videoView.start()
        videoView.setOnClickListener {

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            videoView.setOnInfoListener(object : MediaPlayer.OnInfoListener {
                override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
                    if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                        // Here the video starts
                        shorts.editDurationAndResume(index, (videoView.duration) / 1000)

                        return true
                    }
                    return false
                }
            })
        }
    }
}