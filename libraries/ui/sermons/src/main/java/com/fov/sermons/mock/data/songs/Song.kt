package com.fov.sermons.mock.data.songs

import com.fov.sermons.models.Song
val PAGER_IMAGES = listOf(
"https://picsum.photos/id/10/200/",
"https://picsum.photos/id/10/200/",
"https://picsum.photos/id/1000/200/",
"https://picsum.photos/id/1002/200/"
)
val SONGS = List(20){
    Song(
        songId = "12swsdfdf",
        songName = "Black Excellence $it",
        artwork = "https://picsum.photos/id/1002/200/",
        songLength = "12:00",
        previewPath = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
        path = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
    )
}

