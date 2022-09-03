package com.fov.shorts.mock.data

import com.fov.domain.models.shorts.Short

val SHORTS = List(20){
    com.fov.domain.models.shorts.Short(
        name = "Short $it",
        path = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        id = "123$it"

        )
}