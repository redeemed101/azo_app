package com.fov.domain.remote.mock.shorts

import com.fov.domain.models.shorts.ShortType
import com.fov.domain.models.shorts.ShortsResult
import com.google.gson.Gson


object ShortMockResponse{
    operator fun invoke(): String {
        var obj = ShortsResult(
            shorts = listOf(
                com.fov.domain.models.shorts.Short(
                    name = "Image",
                    content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum",
                    id = "123222",
                    type = ShortType.TEXT.type
                ),
                com.fov.domain.models.shorts.Short(
                    name = "Image",
                    content = "BREAKOUT IN 5 DAYS",
                    id = "123222",
                    type = ShortType.TEXT.type
                ),
                com.fov.domain.models.shorts.Short(
                    name = "Short Video 1",
                    content = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                    id = "123222",
                    type = ShortType.VIDEO.type
                ),
                com.fov.domain.models.shorts.Short(
                    name = "Image",
                    content = "https://picsum.photos/200",
                    id = "123222",
                    type = ShortType.IMAGE.type
                )

            )
        )
        return Gson().toJson(obj)
    }
}

val SHORTS = listOf(
    com.fov.domain.models.shorts.Short(
        name = "Short Video 1",
        content = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        id = "123222",
        type = ShortType.VIDEO.type

        ),
    com.fov.domain.models.shorts.Short(
        name = "Short Image 2",
        content = "https://picsum.photos/200",
        id = "123$",
        type = ShortType.IMAGE.type

    ),
    com.fov.domain.models.shorts.Short(
        name = "Short Text 3",
        content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum",
        id = "123",
        type = ShortType.TEXT.type

    )
)