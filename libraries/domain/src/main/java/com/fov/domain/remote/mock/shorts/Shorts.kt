package com.fov.domain.remote.mock.shorts

import com.fov.domain.models.shorts.Short
import com.fov.domain.models.shorts.ShortType

val SHORTS = listOf(
    com.fov.domain.models.shorts.Short(
        name = "Short Video 1",
        path = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        id = "123222",
        type = ShortType.VIDEO.type

        ),
    com.fov.domain.models.shorts.Short(
        name = "Short Image 2",
        path = "https://picsum.photos/200",
        id = "123$",
        type = ShortType.IMAGE.type

    ),
    com.fov.domain.models.shorts.Short(
        name = "Short Text 3",
        path = "Ready For HIH Zambia",
        id = "123",
        type = ShortType.TEXT.type

    )
)