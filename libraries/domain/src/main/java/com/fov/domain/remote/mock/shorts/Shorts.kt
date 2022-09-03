package com.fov.domain.remote.mock.shorts

import com.fov.domain.models.shorts.Short
import com.fov.domain.models.shorts.ShortType

val SHORTS = List(5){
    com.fov.domain.models.shorts.Short(
        name = "Short Video $it",
        path = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        id = "123$it",
        type = ShortType.VIDEO.type

        )
    com.fov.domain.models.shorts.Short(
        name = "Short Image $it",
        path = "https://picsum.photos/200",
        id = "123$it",
        type = ShortType.IMAGE.type

    )
    com.fov.domain.models.shorts.Short(
        name = "Short Text $it",
        path = "Ready For HIH Zambia",
        id = "123$it",
        type = ShortType.TEXT.type

    )
}