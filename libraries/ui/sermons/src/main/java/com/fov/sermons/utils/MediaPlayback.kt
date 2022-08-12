package com.fov.sermons.utils

import com.google.android.exoplayer2.MediaItem

interface MediaPlayback {

    fun playPause()

    fun forward(durationInMillis: Long)

    fun rewind(durationInMillis: Long)

    fun toggleRepeatMode(repeatMode : Int)

    fun addMediaToPlaylist(media : MediaItem)

    fun addMediaToPlaylist(media : List<MediaItem>)

    fun songReady()
}