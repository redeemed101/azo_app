package com.fov.azo

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import com.fov.domain.BuildConfig
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class YouTubePlayerActivity : YouTubeBaseActivity()  {
    var videoId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.youtube_player_view)

        val ytPlayer = findViewById<YouTubePlayerView>(R.id.ytPlayer)
        val closeButton = findViewById<ImageButton>(R.id.quit)

        closeButton.setOnClickListener {
            finish()
        }

        val id = intent.getStringExtra("videoId")
        if(id != null){
            videoId = id
        }else{
            Toast.makeText(this@YouTubePlayerActivity , "Video player Failed" , Toast.LENGTH_SHORT).show()
            finish()
        }


        ytPlayer.initialize(BuildConfig.YOUTUBE_KEY, object : YouTubePlayer.OnInitializedListener{

            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                p2: Boolean
            ) {
                player?.loadVideo(videoId)
                player?.play()
            }
            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(this@YouTubePlayerActivity , "Video player Failed" , Toast.LENGTH_SHORT).show()
            }
        })


    }

}