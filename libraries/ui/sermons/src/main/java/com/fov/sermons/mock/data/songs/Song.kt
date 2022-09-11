package com.fov.sermons.mock.data.songs

import com.fov.sermons.models.Song
val PAGER_IMAGES = listOf(
"/data/data/com.fov.azo/files/apostle2.jpg",
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
        previewPath = "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3",
        path = "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3"
    )
}

