package com.fov.shorts.mock.data

import com.fov.domain.models.shorts.ShortType

val SHORTS = List(5){
    com.fov.domain.models.shorts.Short(
        name = "Short Video $it",
        content = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        id = "123$it",
        type = ShortType.VIDEO.type,
        createdAt =  "2023-03-04"

        )
    com.fov.domain.models.shorts.Short(
        name = "Short Image $it",
        content = "https://picsum.photos/200",
        id = "123$it",
        type = ShortType.IMAGE.type,
        createdAt =  "2023-03-04"

    )
    com.fov.domain.models.shorts.Short(
        name = "Short Text $it",
        content = "Ready For HIH Zambia",
        id = "123$it",
        type = ShortType.TEXT.type,
        createdAt =  "2023-03-04"

    )
}